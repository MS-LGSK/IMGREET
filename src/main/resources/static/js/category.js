document.addEventListener('DOMContentLoaded', () => {
    const categoryButtonsContainer = document.getElementById('categoryButtons');
    const sidebarContent = document.getElementById('sidebarContent');
    const sidebar = document.getElementById('sidebar');
    const sidebarCloseBtn = document.getElementById('sidebarCloseBtn');

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
            type: category.type
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

            button.addEventListener('click', () => fetchAndDisplaySubTypes(category.id));
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
        switch (item) {
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

    function addImage(input) {
        const file = input.files[0]; // 선택된 파일 가져오기

        if (file) {
            // 새로운 이미지 요소 추가
            const newImage = document.createElement("img");
            newImage.classList.add('img');

            // 이미지 소스 설정
            newImage.src = URL.createObjectURL(file);

            // 이미지 스타일 설정
            newImage.style.position = "absolute"; // 절대 위치로 설정
            newImage.style.top = "10px"; // 원하는 위치 설정
            newImage.style.left = "10px"; // 원하는 위치 설정
            newImage.style.width = "70%";
            newImage.style.height = "70%";
            newImage.style.visibility = "hidden";
            newImage.style.objectFit = "contain";

            // 이미지를 container에 추가
            const container = document.getElementById('container');
            container.style.position = "relative"; // 컨테이너를 relative로 설정
            container.appendChild(newImage);

            // 이미지 표시
            newImage.onload = () => {
                newImage.style.visibility = "visible";
                URL.revokeObjectURL(newImage.src); // 메모리 해제
            };

            window.imageDraggable(newImage);
        } else {
            console.error('No file selected');
        }
    }

    // Component factory mapping
    const componentFactory = {
        '가로 텍스트': createTextComponent,
        '세로 텍스트': createTextComponent,
        '원': createShapeComponent,
        '사각형': createShapeComponent,
        '삼각형': createShapeComponent,
        '이미지 추가': triggerImageUpload
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
                } else if (component.tagName === 'SHAPE') {
                    if (window.addShape) {
                        window.addShape(component);
                    }
                } else if (component.tagName === '이미지 추가') {
                    triggerImageUpload();
                }
            }
        } else {
            console.error('Unknown component type:', item);
        }
    }

    function triggerImageUpload() {
        const input = document.createElement('input');
        input.type = 'file';
        input.accept = 'image/*';
        input.style.display = 'none';

        input.addEventListener('change', (e) => {
            addImage(e.target);
            e.target.value = '';
        });

        document.body.appendChild(input);
        input.click();
        document.body.removeChild(input);
    }

    fetchCategoryIds();
});
