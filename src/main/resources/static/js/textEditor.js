document.addEventListener('DOMContentLoaded', () => {
    const container = document.getElementById('container');

    function initializeTextEditor(text) {
        const editorContainer = document.createElement('div');
        editorContainer.className = 'editor-container';
        editorContainer.classList.add('active');

        editorContainer.appendChild(text);
        container.appendChild(editorContainer);

        const handlePositions = ['br', 'tr', 'bl', 'tl', 'top', 'bottom', 'left', 'right'];
        handlePositions.forEach(pos => {
            const handle = document.createElement('div');
            handle.className = `resize-handle ${pos}`;
            editorContainer.appendChild(handle);
        });

        // Set the initial position of the editor container to be centered in the container
        const containerRect = container.getBoundingClientRect();
        const editorRect = editorContainer.getBoundingClientRect();

        // Calculate initial position
        const initialLeft = (containerRect.width - editorRect.width) / 2;
        const initialTop = (containerRect.height - editorRect.height) / 2;

        editorContainer.style.position = 'absolute';
        editorContainer.style.left = `${initialLeft}px`;
        editorContainer.style.top = `${initialTop}px`;

        let isDragging = false;
        let isResizing = false;
        let offsetX, offsetY, startWidth, startHeight, startX, startY;
        let resizeHandle;

        editorContainer.addEventListener('mousedown', function (e) {
            if (e.target === text) {
                isDragging = true;
                offsetX = e.clientX - editorContainer.getBoundingClientRect().left;
                offsetY = e.clientY - editorContainer.getBoundingClientRect().top;
                editorContainer.style.cursor = 'grabbing';
                editorContainer.classList.add('active');
            } else if (e.target.classList.contains('resize-handle')) {
                isResizing = true;
                resizeHandle = e.target;
                startWidth = editorContainer.offsetWidth;
                startHeight = editorContainer.offsetHeight;
                startX = e.clientX;
                startY = e.clientY;
            }
        });

        document.addEventListener('mousemove', function (e) {
            if (isDragging) {
                const containerRect = container.getBoundingClientRect();
                const editorRect = editorContainer.getBoundingClientRect();
                const x = e.clientX - offsetX;
                const y = e.clientY - offsetY;

                // Restrict movement within the container bounds
                const newX = Math.max(containerRect.left, Math.min(containerRect.right - editorRect.width, x));
                const newY = Math.max(containerRect.top, Math.min(containerRect.bottom - editorRect.height, y));

                editorContainer.style.left = `${newX - containerRect.left}px`;
                editorContainer.style.top = `${newY - containerRect.top}px`;
            } else if (isResizing) {
                const dx = e.clientX - startX;
                const dy = e.clientY - startY;

                if (resizeHandle.classList.contains('br')) {
                    editorContainer.style.width = `${startWidth + dx}px`;
                    editorContainer.style.height = `${startHeight + dy}px`;
                } else if (resizeHandle.classList.contains('bl')) {
                    editorContainer.style.width = `${startWidth - dx}px`;
                    editorContainer.style.height = `${startHeight + dy}px`;
                    editorContainer.style.left = `${editorContainer.offsetLeft + dx}px`;
                } else if (resizeHandle.classList.contains('tr')) {
                    editorContainer.style.width = `${startWidth + dx}px`;
                    editorContainer.style.height = `${startHeight - dy}px`;
                    editorContainer.style.top = `${editorContainer.offsetTop + dy}px`;
                } else if (resizeHandle.classList.contains('tl')) {
                    editorContainer.style.width = `${startWidth - dx}px`;
                    editorContainer.style.height = `${startHeight - dy}px`;
                    editorContainer.style.left = `${editorContainer.offsetLeft + dx}px`;
                    editorContainer.style.top = `${editorContainer.offsetTop + dy}px`;
                } else if (resizeHandle.classList.contains('top')) {
                    editorContainer.style.height = `${startHeight - dy}px`;
                    editorContainer.style.top = `${editorContainer.offsetTop + dy}px`;
                } else if (resizeHandle.classList.contains('bottom')) {
                    editorContainer.style.height = `${startHeight + dy}px`;
                } else if (resizeHandle.classList.contains('left')) {
                    editorContainer.style.width = `${startWidth - dx}px`;
                    editorContainer.style.left = `${editorContainer.offsetLeft + dx}px`;
                } else if (resizeHandle.classList.contains('right')) {
                    editorContainer.style.width = `${startWidth + dx}px`;
                }
            }
        });

        document.addEventListener('mouseup', function () {
            isDragging = false;
            isResizing = false;
            editorContainer.style.cursor = 'grab';
        });

        document.addEventListener('click', function (e) {
            if (!editorContainer.contains(e.target)) {
                editorContainer.classList.remove('active');
                console.log('Clicked outside: Active class removed.');
            }
        });

        text.addEventListener('click', function () {
            editorContainer.classList.add('active');
            console.log('Textarea clicked: Active class added.');
        });

        document.addEventListener('keydown', function (e) {
            if (e.key === 'Backspace') {
                const activeElement = document.activeElement;

                if (activeElement.tagName === 'TEXTAREA') {
                    const editorContainer = activeElement.closest('.editor-container');
                    const isEditorActive = editorContainer && editorContainer.classList.contains('active');

                    const isTextInput = activeElement.value.trim() !== '';
                    const isTextCursor = window.getComputedStyle(activeElement).cursor === 'text';

                    if (isEditorActive) {
                        if (!isTextCursor || !isTextInput) {
                            console.log('Text cursor not active or no text input: Deleting textarea.');
                            e.preventDefault();
                            editorContainer.remove();
                        } else {
                            console.log('Text cursor active: Text deletion allowed.');
                        }
                    } else {
                        console.log('Editor not active: No action taken.');
                    }
                } else {
                    console.log('No active textarea found or activeElement is not textarea.');
                }
            }
        });
    }

    window.initializeTextEditor = initializeTextEditor; // Make function available globally
});
