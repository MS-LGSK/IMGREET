function toggleMenu() {
    const dropdownMenu = document.getElementById("dropdownMenu");
    const dropdownContainer = document.getElementById("dropdownContainer");

    if (dropdownMenu.classList.contains("show")) {
        dropdownMenu.classList.remove("show");
        dropdownMenu.classList.add("hide");
        setTimeout(() => {
            dropdownMenu.classList.remove("hide");
        }, 300); // 애니메이션 시간과 동일하게 설정
    } else {
        dropdownMenu.style.display = 'flex'; // 요소를 표시
        dropdownMenu.classList.add("show");
        dropdownContainer.classList.add("show");
    }
}

document.addEventListener("click", function(event) {
    const dropdownContainer = document.getElementById("dropdownContainer");
    const dropdownMenu = document.getElementById("dropdownMenu");
    const hamburgerBtn = document.querySelector(".hamburger-btn");

    if (!dropdownMenu.contains(event.target) && !hamburgerBtn.contains(event.target)) {
        if (dropdownMenu.classList.contains("show")) {
            dropdownMenu.classList.remove("show");
            dropdownMenu.classList.add("hide");
            setTimeout(() => {
                dropdownMenu.classList.remove("hide");
                dropdownMenu.style.display = 'none'; // 애니메이션이 완료된 후 요소 숨김
                dropdownContainer.classList.remove("show");
            }, 300); // 애니메이션 시간과 동일하게 설정
        }
    }
});
