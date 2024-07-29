document.addEventListener('DOMContentLoaded', () => {
    const container = document.getElementById('container');

    // Initialize text editor
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

        // drag and size
        document.addEventListener('mousemove', function (e) {
            if (isDragging) {
                const x = e.clientX - offsetX;
                const y = e.clientY - offsetY;
                editorContainer.style.left = `${x}px`;
                editorContainer.style.top = `${y}px`;
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

        // 수정 : textarea 클릭 시 active 적용 및 비-active 상태에서 클릭 시 active 해제
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

        // textarea에 텍스트 내용이 있던 없던, 텍스트 편집 커서가 아닌 경우 backspace 시 textarea 삭제
        document.addEventListener('keydown', function (e) {
            if (e.key === 'Backspace') {
                const activeElement = document.activeElement;

                if (activeElement.tagName === 'TEXTAREA') {
                    const editorContainer = activeElement.closest('.editor-container');
                    const isEditorActive = editorContainer && editorContainer.classList.contains('active');

                    // Check for text input and cursor type
                    const isTextInput = activeElement.value.trim() !== '';
                    const isTextCursor = window.getComputedStyle(activeElement).cursor === 'text';

                    if (isEditorActive) {
                        if (!isTextCursor || !isTextInput) {
                            console.log('Text cursor not active or no text input: Deleting textarea.');
                            e.preventDefault(); // Prevent the default backspace action
                            editorContainer.remove(); // Remove the active editorContainer
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

    window.initializeTextEditor = initializeTextEditor; // 함수 전역으로 내보내기
});
