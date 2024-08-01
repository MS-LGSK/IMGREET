document.getElementById("saveButton").addEventListener("click", function(event) {
    event.preventDefault(); // Prevent form submission

    const container = document.getElementById("container");
    const svgElements = Array.from(container.querySelectorAll('svg > *'));
    const nonSvgElements = Array.from(container.querySelectorAll('textarea, img'));

    const components = svgElements.map(svgElement => {
        const subTypeId = svgElement.dataset.subtypeId;
        if (!subTypeId || isNaN(Number(subTypeId))) {
            console.error('SVG 컴포넌트에 올바른 subtypeId가 설정되지 않았습니다:');
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

    const nonSvgComponents = nonSvgElements.map(nonSvgElement => {
        const subTypeId = nonSvgElement.dataset.subtypeId;
        if (!subTypeId || isNaN(Number(subTypeId)) || Number(subTypeId) === 0) {
            console.error('비SVG 컴포넌트에 올바른 subtypeId가 설정되지 않았습니다:');
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
            categoryDetailId: Number(subTypeId) // Convert to integer
        };
    }).filter(component => component !== null);

    const allComponents = components.concat(nonSvgComponents);

    if (allComponents.length === 0) {
        console.error('저장할 컴포넌트가 없습니다.');
        return;
    }

    fetch("/template/save", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(allComponents)
    })
        .then(response => {
            if (response.ok) {
                console.log("템플릿이 성공적으로 저장되었습니다!");
                window.location.href='http://localhost:8080';
            } else {
                return response.text().then(text => { throw new Error(text); });
            }
        })
        .catch(error => {
            console.error("오류:", error);
        });
});
