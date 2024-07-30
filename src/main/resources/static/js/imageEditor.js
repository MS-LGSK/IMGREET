document.addEventListener('DOMContentLoaded', () => {
    let selectedImage = null;
    let selectionBox = null;
    let offsetX, offsetY, startX, startY, startWidth, startHeight;
    let isDragging = false;
    let isResizing = false;

    // 이미지가 드래그 가능하도록 설정하는 함수
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

    // 마우스 이동 시 이미지 드래그 및 리사이즈 처리
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
        } else if (isResizing && selectedImage) {
            const dx = e.clientX - startX;
            const dy = e.clientY - startY;

            selectedImage.style.width = `${Math.max(50, startWidth + dx)}px`;  // Minimum width of 50px
            selectedImage.style.height = `${Math.max(50, startHeight + dy)}px`; // Minimum height of 50px

            updateSelectionBox(selectedImage);
        }
    }

    // 마우스 버튼을 떼면 드래그 및 리사이즈 상태를 종료
    function onMouseUp() {
        isDragging = false;
        isResizing = false;
        document.removeEventListener('mousemove', onMouseMove);
        document.removeEventListener('mouseup', onMouseUp);
    }

    // 선택된 이미지가 아닌 영역을 클릭하면 선택 박스 제거
    document.addEventListener('click', (e) => {
        if (selectedImage && !e.target.closest('.img')) {
            removeSelectionBox();
        }
    });

    // 이미지 선택 처리
    function selectImage(image) {
        // 기존 선택 박스 제거
        if (selectionBox) {
            selectionBox.remove();
        }

        selectedImage = image;
        selectionBox = document.createElement('div');
        selectionBox.classList.add('selection-box');

        updateSelectionBox(image);
        document.getElementById('container').appendChild(selectionBox);
    }

    // 선택 박스 업데이트
    function updateSelectionBox(image) {
        const rect = image.getBoundingClientRect();
        const containerRect = document.getElementById('container').getBoundingClientRect();

        selectionBox.style.position = 'absolute';
        selectionBox.style.border = '2px dashed #00f';
        selectionBox.style.pointerEvents = 'none';
        selectionBox.style.left = `${rect.left - containerRect.left}px`;
        selectionBox.style.top = `${rect.top - containerRect.top}px`;
        selectionBox.style.width = `${rect.width}px`;
        selectionBox.style.height = `${rect.height}px`;

        createResizeHandle(selectionBox);
    }

    // 선택 박스 제거
    function removeSelectionBox() {
        if (selectionBox) {
            selectionBox.remove();
            selectionBox = null;
        }
    }

    // 리사이즈 핸들 생성
    function createResizeHandle(selectionBox) {
        // 기존 리사이즈 핸들 제거 (중복 생성을 방지하기 위해)
        console.log('createResizeHandle 호출');
        const existingHandle = selectionBox.querySelector('.resize-handle');
        if (existingHandle) {
            existingHandle.remove();
        }

        // Resize handle 요소 생성
        const resizeHandle = document.createElement('div');
        resizeHandle.classList.add('resize-handle');
        resizeHandle.style.position = 'absolute';
        resizeHandle.style.width = '10px';
        resizeHandle.style.height = '10px';
        resizeHandle.style.backgroundColor = '#00f';
        resizeHandle.style.cursor = 'nwse-resize';
        resizeHandle.style.pointerEvents = 'auto';

        // 선택 박스의 오른쪽 아래 모서리에 배치
        resizeHandle.style.right = '0px';
        resizeHandle.style.bottom = '0px';

        selectionBox.appendChild(resizeHandle);
        console.log('startResize 들어가기 전');
        resizeHandle.addEventListener('mousedown', startResize);
    }

    // 리사이즈 시작
    function startResize(event) {
        console.log('startResize 호출');
        event.preventDefault();
        isResizing = true;

        // 초기 너비, 높이 및 마우스 위치 기록
        startWidth = selectedImage.offsetWidth;
        startHeight = selectedImage.offsetHeight;
        startX = event.clientX;
        startY = event.clientY;

        // 리사이즈 이벤트 리스너 설정
        document.addEventListener('mousemove', resize);
        document.addEventListener('mouseup', stopResize);
    }

    // 리사이즈 처리
    function resize(event) {
        console.log('resize 호출');
        if (!isResizing || !selectedImage) return;

        const dx = event.clientX - startX;
        const dy = event.clientY - startY;

        // 이미지의 너비와 높이 업데이트
        selectedImage.style.width = `${Math.max(50, startWidth + dx)}px`;  // 최소 너비 50px
        selectedImage.style.height = `${Math.max(50, startHeight + dy)}px`; // 최소 높이 50px

        // 선택 박스도 새로운 크기에 맞게 업데이트
        updateSelectionBox(selectedImage);
    }

    // 리사이즈 종료
    function stopResize() {
        isResizing = false;
        document.removeEventListener('mousemove', resize);
        document.removeEventListener('mouseup', stopResize);
    }

    // 전역에서 imageDraggable 함수 사용 가능
    window.imageDraggable = imageDraggable;
});
