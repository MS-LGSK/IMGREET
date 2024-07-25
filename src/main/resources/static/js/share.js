document.getElementById("detailButton").addEventListener("click", function() {
    this.classList.toggle("close");
    this.classList.toggle("open");
    const detailOptionContainer = document.getElementById("detailOptionContainer");
    if (detailOptionContainer.classList.contains("hidden")) {
        detailOptionContainer.classList.remove("hidden");
        detailOptionContainer.classList.add("visible");
    } else {
        detailOptionContainer.classList.remove("visible");
        detailOptionContainer.classList.add("hidden");
    }
});

document.querySelectorAll('.detailOptionCheckbox').forEach(checkbox => {
    checkbox.addEventListener('change', function() {
        if (this.checked) {
            console.log(this.id + ' is checked');
        } else {
            console.log(this.id + ' is unchecked');
        }
    });
});


// link 클래스의 value값을 copyButton 클래스의 버튼을 클릭하면 클립보드에 복사하기
document.getElementById("copyButton").addEventListener("click", function() {
    const linkInput = document.getElementById("linkInput").value;

    if (navigator.clipboard) {
        navigator.clipboard.writeText(linkInput).then(function() {
            alert("복사 완료!!");
        }).catch(function(error) {
            console.error("복사 실패:", error);
        });
    } else {
        // 클립보드 API가 지원되지 않는 경우 대체 방법 사용
        fallbackCopyTextToClipboard(linkInput);
    }
});

function fallbackCopyTextToClipboard(text) {
    const textArea = document.createElement("textarea");
    textArea.value = text;
    document.body.appendChild(textArea);
    textArea.select();
    try {
        document.execCommand("copy");
        alert("복사 완료!!");
    } catch (err) {
        console.error("복사 실패:", err);
    } finally {
        document.body.removeChild(textArea);
    }
}
