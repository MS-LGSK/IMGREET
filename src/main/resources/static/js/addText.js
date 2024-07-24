document.addEventListener('DOMContentLoaded', () => {
    const container = document.getElementById('container');

    // 사이드바 열기
    function openSidebar(items) {
        const sidebarContent = document.getElementById('sidebarContent');
        sidebarContent.innerHTML = '';

        items.forEach(item => {
            const li = document.createElement('li');
            li.className = 'nav-item';

            const button = document.createElement('button');
            button.className = 'nav-button';
            button.textContent = item;

            button.addEventListener('click', function () {
                handleSidebarButtonClick(item);
            });

            li.appendChild(button);
            sidebarContent.appendChild(li);
        });

        document.getElementById('sidebar').classList.add('open');
    }

    // 텍스트 카테고리의 서브 카테고리 출력
    document.getElementById('text').addEventListener('click', function () {
        openSidebar(['가로 텍스트', '세로 텍스트']);
    });

    // 도형 카테고리의 서브 카테고리 출력
    document.getElementById('shape').addEventListener('click', function () {
        openSidebar(['원', '사각형', '삼각형']);
    });

    // 이미지 카테고리의 서브 카테고리 출력
    document.getElementById('image').addEventListener('click', function () {
        openSidebar(['이미지 업로드']);
    });

    // // 사이드바 외부 클릭 시 닫기
    // document.addEventListener('click', function (event) {
    //     if (!event.target.closest('.fixed-sidebar') && !event.target.closest('.sidebar-nav')) {
    //         document.getElementById('sidebar').classList.remove('open');
    //     }
    // });

    // 사이드바 닫기 버튼 클릭 시 닫기
    document.getElementById('sidebarCloseBtn').addEventListener('click', function () {
        document.getElementById('sidebar').classList.remove('open');
    });

    // 버튼 클릭 시 동작을 처리하는 함수 -> command 패턴으로 변경 예정
    function handleSidebarButtonClick(item) {
        const text = document.createElement('textarea');
        if (item === '가로 텍스트') {
            text.className = 'horizontal-text';
            text.placeholder = '텍스트를 입력하세요.';
        } else if (item === '세로 텍스트') {
            text.className = 'vertical-text';
            text.placeholder = '텍스트를 입력하세요.';
        }

        initializeTextEditor(text);
    }

    // 가로 텍스트 버튼을 클릭 시 호출될 함수
    function initializeTextEditor(text) {
        const editorContainer = document.createElement('div');
        editorContainer.className = 'editor-container';

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
            // } else {
            //     // editorContainer.classList.add('active');
            // }
        });

        function handleDeleteKeyDown(e) {
            const activeElement = document.activeElement;
            const isTextInput = activeElement.tagName === 'INPUT' ||
                activeElement.tagName === 'TEXTAREA' ||
                activeElement.isContentEditable;

            if (e.key === 'Backspace' && !isTextInput) {
                editorContainer.remove();
            }
        }

        // 클릭 이벤트 핸들러 함수
        function handleClick() {
            editorContainer.classList.add('active');
        }

        // 이벤트 리스너 등록
        editorContainer.addEventListener('click', handleClick);
        document.addEventListener('keydown', handleDeleteKeyDown);

        document.addEventListener('mousemove', function (e) {
            if (isDragging) {
                editorContainer.style.left = `${e.clientX - offsetX}px`;
                editorContainer.style.top = `${e.clientY - offsetY}px`;
            } else if (isResizing) {
                const rect = editorContainer.getBoundingClientRect();
                const aspectRatio = startWidth / startHeight;

                if (resizeHandle.classList.contains('br')) {
                    const newWidth = Math.max(200, e.clientX - rect.left);
                    const newHeight = newWidth / aspectRatio;
                    editorContainer.style.width = `${newWidth}px`;
                    editorContainer.style.height = `${Math.max(100, newHeight)}px`;
                } else if (resizeHandle.classList.contains('tr')) {
                    const newWidth = Math.max(200, e.clientX - rect.left);
                    const newHeight = newWidth / aspectRatio;
                    editorContainer.style.width = `${newWidth}px`;
                    editorContainer.style.height = `${Math.max(100, newHeight)}px`;
                    editorContainer.style.top = `${e.clientY - startY}px`;
                } else if (resizeHandle.classList.contains('bl')) {
                    const newWidth = Math.max(200, e.clientX - rect.left);
                    const newHeight = newWidth / aspectRatio;
                    editorContainer.style.width = `${newWidth}px`;
                    editorContainer.style.height = `${Math.max(100, newHeight)}px`;
                    editorContainer.style.left = `${e.clientX - startX}px`;
                } else if (resizeHandle.classList.contains('tl')) {
                    const newWidth = Math.max(200, e.clientX - rect.left);
                    const newHeight = newWidth / aspectRatio;
                    editorContainer.style.width = `${newWidth}px`;
                    editorContainer.style.height = `${Math.max(100, newHeight)}px`;
                    editorContainer.style.left = `${e.clientX - startX}px`;
                    editorContainer.style.top = `${e.clientY - startY}px`;
                } else if (resizeHandle.classList.contains('top')) {
                    const newHeight = Math.max(100, startHeight + startY - e.clientY);
                    const newWidth = newHeight * aspectRatio;
                    editorContainer.style.width = `${newWidth}px`;
                    editorContainer.style.height = `${newHeight}px`;
                    editorContainer.style.top = `${e.clientY - startY}px`;
                } else if (resizeHandle.classList.contains('bottom')) {
                    editorContainer.style.height = `${startHeight + e.clientY - startY}px`;
                } else if (resizeHandle.classList.contains('left')) {
                    const newWidth = Math.max(200, startWidth + startX - e.clientX);
                    const newHeight = newWidth / aspectRatio;
                    editorContainer.style.width = `${newWidth}px`;
                    editorContainer.style.height = `${newHeight}px`;
                    editorContainer.style.left = `${e.clientX - startX}px`;
                } else if (resizeHandle.classList.contains('right')) {
                    editorContainer.style.width = `${startWidth + e.clientX - startX}px`;
                }
            }
        });

        document.addEventListener('mouseup', function () {
            isDragging = false;
            isResizing = false;
            editorContainer.classList.remove('active');
            editorContainer.style.cursor = 'grab';
        });
    }
});
