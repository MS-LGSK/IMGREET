document.addEventListener('DOMContentLoaded', () => {
    const subTypeMap = {};
    const categoryButtonsContainer = document.getElementById('categoryButtons');
    const sidebarContent = document.getElementById('sidebarContent');
    const sidebar = document.getElementById('sidebar');
    const sidebarCloseBtn = document.getElementById('sidebarCloseBtn');
    const forbiddenFree = document.getElementById('forbiddenFree');
    const mapContainer = document.getElementById('map-container');
    const commentContainer = document.getElementById('comment-container');

    let currentSubTypeId = null;

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

    function storeCategoryIds(data) {
        const categories = data.map(category => ({
            id: category.id,
            type: category.type,
            free: category.free
        }));
        localStorage.setItem('categories', JSON.stringify(categories));
    }

    function storeSubTypeMap(categoryId, subTypes) {
        subTypeMap[categoryId] = subTypes.reduce((map, subType) => {
            map[subType.subType] = subType.id;
            return map;
        }, {});
    }

    function displayCategoryButtons(categoryIds) {
        categoryButtonsContainer.innerHTML = '';
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

    function fetchAndDisplaySubTypes(categoryId) {
        fetch(`/${categoryId}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.json();
            })
            .then(subTypes => {
                storeSubTypeMap(categoryId, subTypes);
                openSidebar(subTypes);
            })
            .catch(error => {
                console.error('Error fetching subtypes: ', error);
            });
    }

    function openSidebar(subTypes) {
        sidebarContent.innerHTML = '';

        subTypes.forEach(subType => {
            const button = document.createElement('button');
            button.className = 'nav-button';
            button.textContent = subType.subType;
            button.dataset.subTypeId = subType.id;

            button.addEventListener('click', () => handleSidebarButtonClick(subType.subType, subType.id));
            sidebarContent.appendChild(button);
        });
        sidebar.classList.add('open');
    }

    sidebarCloseBtn.addEventListener('click', () => {
        sidebar.classList.remove('open');
    });

    function createTextComponent(item, subTypeId) {
        const text = document.createElement('textarea');
        text.className = item.includes('가로') ? 'horizontal-text' : 'vertical-text';
        text.placeholder = '텍스트를 입력하세요.';
        text.dataset.subtypeId = subTypeId;
        return text;
    }

    function createShapeComponent(item, subTypeId) {
        let shape;
        switch (item) {
            case '원':
                shape = window.createCircle(subTypeId);
                break;
            case '사각형':
                shape = window.createRectangle(subTypeId);
                break;
            case '삼각형':
                shape = window.createTriangle(subTypeId);
                break;
            default:
                console.error('알 수 없는 도형 타입:', item);
                return null;
        }
        shape.dataset.subtypeId = subTypeId;
        return shape;
    }

    function addImage(input, subTypeId) {
        const file = input.files[0];
        if (file) {
            const newImage = document.createElement("img");
            newImage.classList.add('img');
            newImage.src = URL.createObjectURL(file);
            newImage.style.position = "absolute";
            newImage.style.top = "0px";
            newImage.style.left = "0px";
            newImage.style.width = "70%";
            newImage.style.height = "70%";
            newImage.style.visibility = "visible";
            newImage.style.objectFit = "contain";
            newImage.dataset.subtypeId = subTypeId;

            const container = document.getElementById('container');
            container.style.position = "relative";
            container.appendChild(newImage);
            newImage.onload = () => {
                newImage.style.visibility = "visible";
                URL.revokeObjectURL(newImage.src);
            };
            window.imageDraggable(newImage);
        } else {
            console.error('선택된 파일이 없습니다');
        }
    }

    const componentFactory = {
        '가로 텍스트': createTextComponent,
        '세로 텍스트': createTextComponent,
        '원': createShapeComponent,
        '사각형': createShapeComponent,
        '삼각형': createShapeComponent,
        '이미지 추가': triggerImageUpload
    };

    function handleSidebarButtonClick(item, subTypeId) {
        currentSubTypeId = subTypeId;
        const createComponent = componentFactory[item];

        if (createComponent) {
            const component = createComponent(item, subTypeId);

            if (component) {
                component.dataset.subtypeId = subTypeId;

                if (component.tagName === 'TEXTAREA') {
                    if (window.initializeTextEditor) {
                        window.initializeTextEditor(component);
                    }
                } else if (component.tagName === 'circle' || component.tagName === 'rect' || component.tagName === 'polygon') {
                    if (window.addShape) {
                        window.addShape(component);
                    }
                } else if (component.tagName === 'IMG') {
                    triggerImageUpload();
                }
            }
        } else {
            console.error('알 수 없는 컴포넌트 타입:', item);
        }
    }

    function triggerImageUpload() {
        const input = document.createElement('input');
        input.type = 'file';
        input.accept = 'image/*';
        input.style.display = 'none';

        input.addEventListener('change', (e) => {
            addImage(e.target, currentSubTypeId);
            e.target.value = '';
        });

        document.body.appendChild(input);
        input.click();
        document.body.removeChild(input);
    }

    const categoryFactory = {
        '지도': createMapCategory,
        '댓글': createCommentCategory
    };

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