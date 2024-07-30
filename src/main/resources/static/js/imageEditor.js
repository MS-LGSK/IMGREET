document.addEventListener('DOMContentLoaded', () => {
    let selectedImage = null;
    let selectionBox = null;
    let offsetX, offsetY;
    let isDragging = false;

    function imageDraggable(image) {
        image.addEventListener('mousedown', (e) => {
            e.preventDefault();
            selectImage(image);

            offsetX = e.clientX - image.getBoundingClientRect().left;
            offsetY = e.clientY - image.getBoundingClientRect().top;
            isDragging = true;

            document.addEventListener('mousemove', onMouseMove);
            document.addEventListener('mouseup', onMouseUp);
        });
    }

    function onMouseMove(e) {
        if (isDragging && selectedImage) {
            const container = document.getElementById('container');
            const containerRect = container.getBoundingClientRect();
            const imageRect = selectedImage.getBoundingClientRect();

            let newLeft = e.clientX - offsetX;
            let newTop = e.clientY - offsetY;

            // 이미지가 container의 경계 안에 있도록 제한
            newLeft = Math.max(0, Math.min(containerRect.width - imageRect.width, newLeft - containerRect.left));
            newTop = Math.max(0, Math.min(containerRect.height - imageRect.height, newTop - containerRect.top));

            selectedImage.style.left = `${newLeft}px`;
            selectedImage.style.top = `${newTop}px`;

            // 선택 박스도 이미지와 함께 이동
            if (selectionBox) {
                updateSelectionBox(selectedImage);
            }
        }
    }

    function onMouseUp() {
        isDragging = false;  // 드래그 종료
        selectedImage = null;
        document.removeEventListener('mousemove', onMouseMove);
        document.removeEventListener('mouseup', onMouseUp);
    }

    function selectImage(image) {
        // 기존 선택 박스 제거
        if (selectionBox) {
            selectionBox.remove();
        }

        selectedImage = image;
        selectionBox = document.createElement('div');
        selectionBox.classList.add('selection-box');

        updateSelectionBox(image);
        document.getElementById('container').appendChild(selectionBox); // 선택 박스를 컨테이너에 추가
    }

    function updateSelectionBox(image) {
        const rect = image.getBoundingClientRect();
        const containerRect = document.getElementById('container').getBoundingClientRect();

        selectionBox.style.position = 'absolute';
        selectionBox.style.border = '2px dashed #00f';
        selectionBox.style.pointerEvents = 'none'; // 선택 박스를 클릭할 수 없게 설정
        selectionBox.style.left = `${rect.left - containerRect.left}px`;
        selectionBox.style.top = `${rect.top - containerRect.top}px`;
        selectionBox.style.width = `${rect.width}px`;
        selectionBox.style.height = `${rect.height}px`;

        createResizeHandle(selectionBox);
    }

    function createResizeHandle(selectionBox) {
        // Resize handle 요소 생성
        const resizeHandle = document.createElement('div');
        resizeHandle.style.position = 'absolute';
        resizeHandle.style.width = '10px';
        resizeHandle.style.height = '10px';
        resizeHandle.style.backgroundColor = '#00f';
        resizeHandle.style.cursor = 'nwse-resize';

        // 선택 박스의 오른쪽 아래 모서리에 배치
        resizeHandle.style.right = '-10px';
        resizeHandle.style.bottom = '-10px';

        selectionBox.appendChild(resizeHandle);
    }

    window.imageDraggable = imageDraggable;
});
