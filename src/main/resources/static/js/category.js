document.addEventListener('DOMContentLoaded', () => {
    const categoryButtonsContainer = document.getElementById('categoryButtons');
    const sidebarContent = document.getElementById('sidebarContent');
    const sidebar = document.getElementById('sidebar');
    const sidebarCloseBtn = document.getElementById('sidebarCloseBtn');
    const forbiddenFree = document.getElementById('forbiddenFree');
    const mapContainer = document.getElementById('map-container');
    const commentContainer = document.getElementById('comment-container');

    function fetchCategoryIds() {
        fetch('/category')
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                if (Array.isArray(data)) {
                    storeCategoryIds(data);
                    displayCategoryButtons(data);
                } else {
                    console.error('Fetched data is not an array:', data);
                }
            })
            .catch(error => {
                console.error('Error fetching category IDs: ', error);
            });
    }

    // local storage에 category ID 저장
    function storeCategoryIds(data) {
        const categories = data.map(category => ({
            id: category.id,
            type: category.type,
            free : category.free
        }));
        localStorage.setItem('categories', JSON.stringify(categories));
    }

    // sidebar에 카테고리 버튼 표시
    function displayCategoryButtons(categoryIds) {
        categoryButtonsContainer.innerHTML = ''; // 이전 내용 삭제

        categoryIds.forEach(category => {
            const button = document.createElement('button');
            button.className = 'category-button';
            button.textContent = category.type;
            button.dataset.categoryId = category.id;
            button.dataset.categoryFree = category.free;

            button.addEventListener('click', () => {
                fetchAndDisplaySubTypes(category.id);
                checkAndDisplayCategory(category.type, category.free);
            });
            categoryButtonsContainer.appendChild(button);
        });
    }

    // category ID에 대한 subType 표시
    function fetchAndDisplaySubTypes(categoryId) {
        fetch(`/${categoryId}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.json();
            })
            .then(subTypes => {
                openSidebar(subTypes);
            })
            .catch(error => {
                console.error('Error fetching subtypes: ', error);
            });
    }

    // open sidebar
    function openSidebar(subTypes) {
        sidebarContent.innerHTML = ''; // 이전 내용 삭제

        subTypes.forEach(subType => {
            const button = document.createElement('button');
            button.className = 'nav-button';
            button.textContent = subType.subType;

            button.addEventListener('click', () => handleSidebarButtonClick(subType.subType));
            sidebarContent.appendChild(button);
        });
        sidebar.classList.add('open');
    }

    // close sidebar
    sidebarCloseBtn.addEventListener('click', () => {
        sidebar.classList.remove('open');
    });

    function createTextComponent(item) {
        const text = document.createElement('textarea');
        const orientation = item.includes('가로') ? 'horizontal' : 'vertical';
        text.className = `${orientation}-text`;
        text.placeholder = '텍스트를 입력하세요.';
        return text;
    }

    function createShapeComponent(item) {
        switch(item) {
            case '원':
                return window.createCircle();
            case '사각형':
                return window.createRectangle();
            case '삼각형':
                return window.createTriangle();
            default:
                console.error('Unknown shape type:', item);
                return null;
        }
    }

    // Component factory mapping
    const componentFactory = {
        '가로 텍스트': createTextComponent,
        '세로 텍스트': createTextComponent,
        '원': createShapeComponent,
        '사각형': createShapeComponent,
        '삼각형': createShapeComponent
    }

    function handleSidebarButtonClick(item) {
        const createComponent = componentFactory[item];

        if (createComponent) {
            const component = createComponent(item);

            if (component) {
                if (component.tagName === 'TEXTAREA') {
                    if (window.initializeTextEditor) {
                        window.initializeTextEditor(component);
                    }
                } else {
                    if (window.addShape) {
                        window.addShape(component);
                    }
                }
            }
        } else {
            console.error('Unknown component type:', item);
        }
    }

    const categoryFactory = {
        '지도' : createMapCategory,
        '댓글' : createCommentCategory
    }

    function checkAndDisplayCategory(category, free) {
        mapContainer.style.display = 'none';
        commentContainer.style.display = 'none';

        if (!free) {
            forbiddenFree.style.display = 'block';
        } else {
            forbiddenFree.style.display = 'none';
        }

        const createCategory = categoryFactory[category];
        if (createCategory) {
            createCategory();
        }
    }

    function createMapCategory() {
        mapContainer.style.display = 'block';
    }

    function createCommentCategory() {
        commentContainer.style.display = 'block';
    }

    fetchCategoryIds();

});
