document.addEventListener("DOMContentLoaded", function() {
    // 서버에서 전달된 HTML을 가져와서 통합합니다.
    const componentHtmlElement = document.getElementById("componentHtml");

    if (componentHtmlElement) {
        const componentHtml = componentHtmlElement.innerHTML;

        // HTML을 DOM으로 변환하여 요소를 추가합니다.
        function integrateComponents() {
            const container = document.getElementById("container");
            if (container) {
                container.innerHTML = componentHtml; // 서버에서 제공한 HTML을 컨테이너에 추가합니다.

                // SVG 및 텍스트 요소의 스타일을 설정합니다.
                const svgElements = container.querySelectorAll('svg');
                svgElements.forEach(svg => {
                    // SVG 요소의 스타일을 적용하거나 조정합니다.
                    svg.style.position = 'absolute';
                });

                const textElements = container.querySelectorAll('textarea, img');
                textElements.forEach(textElement => {
                    textElement.style.position = 'absolute';
                });
            }
        }

        integrateComponents();
    } else {
        console.warn('componentHtml 요소가 없습니다.');
    }

    // 저장 버튼 클릭 이벤트 핸들러
    document.getElementById("saveButton").addEventListener("click", function(event) {
        event.preventDefault();

        const title = document.getElementById("title").value;
        const container = document.getElementById("container");

        if (container) {
            // SVG 및 비SVG 컴포넌트를 구분합니다.
            const svgElements = Array.from(container.querySelectorAll('svg *'));
            const nonSvgElements = Array.from(container.querySelectorAll('textarea, img'));

            // SVG 컴포넌트를 처리합니다.
            const components = svgElements.map(svgElement => {
                const subTypeId = svgElement.dataset.subtypeId;
                if (!subTypeId || isNaN(Number(subTypeId))) {
                    console.error('SVG 컴포넌트에 올바른 subtypeId가 설정되지 않았습니다:', svgElement);
                    return null;
                }

                return {
                    content: svgElement.outerHTML,
                    x: parseInt(svgElement.style.left || 0, 10),
                    y: parseInt(svgElement.style.top || 0, 10),
                    width: parseInt(svgElement.style.width || 100, 10),
                    height: parseInt(svgElement.style.height || 100, 10),
                    rotation: parseFloat(svgElement.style.transform.replace('rotate(', '').replace('deg)', '') || 0),
                    categoryDetailId: Number(subTypeId)
                };
            }).filter(component => component !== null);

            // 비SVG 컴포넌트를 처리합니다.
            const nonSvgComponents = nonSvgElements.map(nonSvgElement => {
                const subTypeId = nonSvgElement.dataset.subtypeId;
                if (!subTypeId || isNaN(Number(subTypeId)) || Number(subTypeId) === 0) {
                    console.error('비SVG 컴포넌트에 올바른 subtypeId가 설정되지 않았습니다:', nonSvgElement);
                    return null;
                }

                let content;
                if (nonSvgElement.tagName === 'TEXTAREA') {
                    content = `<textarea class="${nonSvgElement.className}" placeholder="${nonSvgElement.placeholder}" data-subtype-id="${subTypeId}">${nonSvgElement.value}</textarea>`;
                } else {
                    content = nonSvgElement.outerHTML;
                }

                return {
                    content: content,
                    x: parseInt(nonSvgElement.style.left || 0, 10),
                    y: parseInt(nonSvgElement.style.top || 0, 10),
                    width: parseInt(nonSvgElement.style.width || 100, 10),
                    height: parseInt(nonSvgElement.style.height || 100, 10),
                    rotation: parseFloat(nonSvgElement.style.transform.replace('rotate(', '').replace('deg)', '') || 0),
                    categoryDetailId: Number(subTypeId)
                };
            }).filter(component => component !== null);

            const allComponents = components.concat(nonSvgComponents);

            if (allComponents.length === 0) {
                console.error('저장할 컴포넌트가 없습니다.');
                return;
            }

            // 캡처 함수 정의 필요
            captureCanvasAsImage().then(thumbnailImage => {
                fetch("/template/save", {
                    method: "POST",
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        title: title,
                        componentList: allComponents,
                        imageUrl: thumbnailImage
                    })
                })
                    .then(response => {
                        if (response.ok) {
                            console.log("템플릿이 성공적으로 저장되었습니다!");
                            window.location.href = 'http://localhost:8080';
                        } else {
                            return response.text().then(text => { throw new Error(text); });
                        }
                    })
                    .catch(error => {
                        console.error("오류:", error);
                    });
            }).catch(error => {
                console.error("캔버스 캡처 오류:", error);
            });
        }
    });
});
