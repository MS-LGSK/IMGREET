document.addEventListener('DOMContentLoaded', () => {
    let selectedImage = null;
    let offsetX, offsetY;
    let isDragging = false;

    function imageDraggable(image) {
        image.addEventListener('mousedown', (e) => {
            e.preventDefault();
            selectedImage = image;

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
            selectedImage.style.top = `${newTop}px`
        }
    }

    function onMouseUp() {
        isDragging = false;  // 드래그 종료
        selectedImage = null;
        document.removeEventListener('mousemove', onMouseMove);
        document.removeEventListener('mouseup', onMouseUp);
    }

    window.imageDraggable = imageDraggable;
});
