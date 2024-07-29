document.addEventListener('DOMContentLoaded', () => {
    const container = document.getElementById('container');
    const svgNS = "http://www.w3.org/2000/svg";
    const svg = document.createElementNS(svgNS, 'svg');
    svg.setAttribute('width', '100%');
    svg.setAttribute('height', '100%');
    container.appendChild(svg);

    let currentSelectedShape = null;        // 현재 선택된 도형
    let selectionBox = null;                // 선택된 도형 주위에 표시되는 선택 상자
    let resizeHandle = null;                // 크기 조절할 수 있는 변수
    let dragStart = { x: 0, y: 0 };         // 도형 드래그 시작 시 마우스 포인터 초기 좌표

    // Create SVG shape component
    function createShapeElement(shapeType, attributes) {
        const shape = document.createElementNS(svgNS, shapeType);
        for (let attribute in attributes) {
            shape.setAttribute(attribute, attributes[attribute]);
        }

        shape.classList.add('shape'); // 도형에 클래스 추가
        shape.addEventListener('mouseup', selectShape);
        shape.addEventListener('mousedown', startDrag);

        return shape;
    }

    function createCircle() {
        return createShapeElement('circle', {
            cx: 50, cy: 50, r: 25,
            stroke: 'black', 'stroke-width': 2, fill: 'transparent'
        });
    }

    // Add shape in SVG
    function addShape(shape) {
        svg.appendChild(shape);
    }

    function selectShape(event) {
        if (currentSelectedShape) {
            currentSelectedShape.setAttribute('stroke-width', currentSelectedShape.originalStrokeWidth);
            removeSelectionBox();
        }

        currentSelectedShape = event.target;
        currentSelectedShape.originalStrokeWidth = currentSelectedShape.getAttribute('stroke-width');
        currentSelectedShape.setAttribute('stroke-width', parseFloat(currentSelectedShape.originalStrokeWidth) + 2);
        createSelectionBox(currentSelectedShape);
    }

    // 도형 이외의 영역 클릭 시 두께 원래대로
    document.addEventListener('click', (e) => {
        // 클릭된 요소가 도형, 선택 상자, 크기 조절 핸들이 아닌 경우
        if (currentSelectedShape && !e.target.closest('.shape, .selection-box, .resize-handle')) {
            currentSelectedShape.setAttribute('stroke-width', currentSelectedShape.originalStrokeWidth);
            currentSelectedShape = null;
            removeSelectionBox();
        }
    });

    window.createCircle = createCircle;
    window.addShape = addShape;
});
