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

    // 선택 상자 생성 함수
    function createSelectionBox(shape) {
        const bbox = shape.getBBox();
        selectionBox = document.createElementNS(svgNS, 'rect');
        selectionBox.setAttribute('x', bbox.x - 5);
        selectionBox.setAttribute('y', bbox.y - 5);
        selectionBox.setAttribute('width', bbox.width + 10);
        selectionBox.setAttribute('height', bbox.height + 10);
        selectionBox.setAttribute('stroke', 'blue');
        selectionBox.setAttribute('stroke-dasharray', '3');
        selectionBox.setAttribute('fill', 'none');
        selectionBox.classList.add('selection-box'); // 선택 상자에 클래스 추가
        svg.appendChild(selectionBox);

        createResizeHandle(bbox);
    }

    // 선택 상자 제거 함수
    function removeSelectionBox() {
        if (selectionBox) {
            selectionBox.remove();
            selectionBox = null;
        }
        if (resizeHandle) {
            resizeHandle.remove();
            resizeHandle = null;
        }
    }

    // 크기 조절 핸들 생성 함수
    function createResizeHandle(bbox) {
        resizeHandle = document.createElementNS(svgNS, 'rect');
        resizeHandle.setAttribute('x', bbox.x + bbox.width + 5);
        resizeHandle.setAttribute('y', bbox.y + bbox.height + 5);
        resizeHandle.setAttribute('width', 10);
        resizeHandle.setAttribute('height', 10);
        resizeHandle.setAttribute('fill', 'blue');
        resizeHandle.classList.add('resize-handle'); // 크기 조절 핸들에 클래스 추가
        svg.appendChild(resizeHandle);

        resizeHandle.addEventListener('mousedown', startResize);
    }

    function startResize(event) {
        event.preventDefault();
        const bbox = currentSelectedShape.getBBox();
        // `resizeStart`를 설정하여 초기 마우스 좌표와 도형의 크기를 저장합니다
        resizeStart = {
            x: event.clientX,
            y: event.clientY,
            width: bbox.width,
            height: bbox.height
        };
        document.addEventListener('mousemove', resize);
        document.addEventListener('mouseup', stopResize);
    }

    function resize(event) {
        // 초기 마우스 좌표와 도형의 크기
        const dx = event.clientX - resizeStart.x;
        const dy = event.clientY - resizeStart.y;
        const newWidth = Math.max(resizeStart.width + dx, 0); // 너비를 0보다 크게 유지
        const newHeight = Math.max(resizeStart.height + dy, 0); // 높이를 0보다 크게 유지

        if (currentSelectedShape.tagName === 'circle') {
            const newRadius = Math.min(newWidth, newHeight) / 2;
            currentSelectedShape.setAttribute('r', newRadius);
        } else {
            currentSelectedShape.setAttribute('width', newWidth);
            currentSelectedShape.setAttribute('height', newHeight);
        }

        updateSelectionBox();
    }

    function stopResize() {
        document.removeEventListener('mousemove', resize);
        document.removeEventListener('mouseup', stopResize);
    }

    // 도형 드래그 시작
    function startDrag(event) {
        dragStart.x = event.clientX;
        dragStart.y = event.clientY;
        document.addEventListener('mousemove', drag);
        document.addEventListener('mouseup', stopDrag);
    }

    // 도형 드래그 중
    function drag(event) {
        const dx = event.clientX - dragStart.x;
        const dy = event.clientY - dragStart.y;

        if (currentSelectedShape.tagName === 'circle') {
            const cx = parseFloat(currentSelectedShape.getAttribute('cx')) + dx;
            const cy = parseFloat(currentSelectedShape.getAttribute('cy')) + dy;
            currentSelectedShape.setAttribute('cx', cx);
            currentSelectedShape.setAttribute('cy', cy);
        } else {
            const x = parseFloat(currentSelectedShape.getAttribute('x')) + dx;
            const y = parseFloat(currentSelectedShape.getAttribute('y')) + dy;
            currentSelectedShape.setAttribute('x', x);
            currentSelectedShape.setAttribute('y', y);
        }

        dragStart.x = event.clientX;
        dragStart.y = event.clientY;

        updateSelectionBox();
    }

    // 도형 드래그 종료
    function stopDrag() {
        document.removeEventListener('mousemove', drag);
        document.removeEventListener('mouseup', stopDrag);
    }

    // 선택 상자 및 크기 조절 핸들 업데이트
    function updateSelectionBox() {
        const bbox = currentSelectedShape.getBBox();
        selectionBox.setAttribute('x', bbox.x - 5);
        selectionBox.setAttribute('y', bbox.y - 5);
        selectionBox.setAttribute('width', bbox.width + 10);
        selectionBox.setAttribute('height', bbox.height + 10);

        resizeHandle.setAttribute('x', bbox.x + bbox.width + 5);
        resizeHandle.setAttribute('y', bbox.y + bbox.height + 5);
    }

    document.addEventListener('keydown', (e) => {
        if(e.key === 'Backspace' && currentSelectedShape) {
            svg.removeChild(currentSelectedShape);
            currentSelectedShape = null;
            removeSelectionBox();
        }
    });

    window.createCircle = createCircle;
    window.addShape = addShape;
});
