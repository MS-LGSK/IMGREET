// document.getElementById("detailButton").addEventListener("click", function () {
//     this.classList.toggle("close");
//     this.classList.toggle("open");
//     const detailOptionContainer = document.getElementById("detailOptionContainer");
//     if (detailOptionContainer.classList.contains("hidden")) {
//         detailOptionContainer.classList.remove("hidden");
//         detailOptionContainer.classList.add("visible");
//     } else {
//         detailOptionContainer.classList.remove("visible");
//         detailOptionContainer.classList.add("hidden");
//     }
// });

document.querySelectorAll(".detailOptionCheckbox").forEach((checkbox) => {
    checkbox.addEventListener("change", function () {
        if (this.checked) {
            console.log(this.id + " is checked");
        } else {
            console.log(this.id + " is unchecked");
        }
    });
});

// link 클래스의 value값을 copyButton 클래스의 버튼을 클릭하면 클립보드에 복사하기
document.getElementById("copyButton").addEventListener("click", function () {
    const linkInput = document.getElementById("linkInput").value;

    if (navigator.clipboard) {
        navigator.clipboard
            .writeText(linkInput)
            .then(function () {
                alert("복사 완료!!");
            })
            .catch(function (error) {
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

document.addEventListener("DOMContentLoaded", function () {
    const mapContainer = document.getElementById("map");

    // 서버에서 좌표 데이터를 가져오는 함수
    async function fetchLocation() {
        try {
            const response = await fetch("/location");
            if (response.ok) {
                const location = await response.json();
                initMap(location.latitude, location.longitude);
            } else {
                console.error("Failed to fetch location data.");
            }
        } catch (error) {
            console.error("Error fetching location data:", error);
        }
    }

    // 지도를 초기화하는 함수
    function initMap(lat, lng) {
        const mapOption = {
            center: new kakao.maps.LatLng(lat, lng),
            level: 2,
        };

        const map = new kakao.maps.Map(mapContainer, mapOption);

        const position = new kakao.maps.LatLng(lat, lng);

        const marker = new kakao.maps.Marker({
            position: position,
            clickable: true,
        });

        marker.setMap(map);

        const iwContent = `
            <div style="padding:5px;">
                <h4>장소 이름</h4>
                <p>여기에 장소의 간단한 설명이 들어갑니다.<br>
                <a href="https://map.kakao.com/link/map/장소이름,${lat},${lng}" style="color:blue" target="_blank">큰 지도보기</a>
                <a href="https://map.kakao.com/link/to/장소이름,${lat},${lng}" style="color:blue" target="_blank">길찾기</a></p>
            </div>
        `;

        const infowindow = new kakao.maps.InfoWindow({
            content: iwContent,
            removable: true,
        });

        kakao.maps.event.addListener(marker, "click", function () {
            infowindow.open(map, marker);
        });
    }

    // 좌표 데이터를 가져와서 지도를 초기화
    fetchLocation();
});

// comment
document.getElementById('submitComment').addEventListener('click', function () {
    const nickname = document.getElementById('nickname').value;
    const password = document.getElementById('password').value;
    const comment = document.getElementById('comment').value;

    if (nickname && password && comment) {
        const commentDisplay = document.querySelector('.commentDisplay');
        const newComment = document.createElement('div');
        newComment.classList.add('comment');
        newComment.innerHTML = `
            <div class="commentBox">
                <div class="nickname">${nickname}</div>
                <div class="dropdown">
                    <button class="dropdownBtn">
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="16" height="16" fill="currentColor">
                            <path d="M12 8a2 2 0 100-4 2 2 0 000 4zm0 4a2 2 0 100-4 2 2 0 000 4zm0 4a2 2 0 100-4 2 2 0 000 4z"/>
                        </svg>
                    </button>
                    <div class="dropdown-content">
                        <a href="#" class="deleteComment">댓글 삭제하기</a>
                        <a href="#" class="reportComment">댓글 신고하기</a>
                    </div>
                </div>
            </div>
            <div class="timestamp">${new Date().toLocaleString()}</div>
            <div class="text">${comment}</div>
        `;
        commentDisplay.appendChild(newComment);

        // Clear the input fields
        document.getElementById('nickname').value = '';
        document.getElementById('password').value = '';
        document.getElementById('comment').value = '';

        // Add event listeners for the new comment
        newComment.querySelector('.dropdownBtn').addEventListener('click', function(event) {
            event.stopPropagation(); // Prevent event bubbling
            const dropdownContent = newComment.querySelector('.dropdown-content');
            const isActive = dropdownContent.style.display === 'block';

            // Close all dropdowns
            document.querySelectorAll('.dropdown-content').forEach(function(content) {
                content.style.display = 'none';
            });

            // Toggle the clicked dropdown
            dropdownContent.style.display = isActive ? 'none' : 'block';
        });

        newComment.querySelector('.deleteComment').addEventListener('click', function() {
            commentDisplay.removeChild(newComment);
        });

        newComment.querySelector('.reportComment').addEventListener('click', function() {
            document.getElementById('report-form-overlay').style.display = 'block';
            document.getElementById('report-form').style.display = 'block';
        });
    } else {
        alert('모든 필드를 입력하세요.');
    }
});


// 댓글 삭제 이벤트

// 댓글 삭제 버튼 클릭 이벤트
document.addEventListener('click', function (event) {
    if (event.target.classList.contains('deleteComment')) {
        event.preventDefault(); // 기본 링크 동작 방지
        const commentElement = event.target.closest('.comment');
        const deletePasswordOverlay = document.getElementById('delete-password-overlay');
        const deletePasswordForm = document.getElementById('delete-password-form');
        const confirmDeleteButton = document.getElementById('confirm-delete');
        const cancelDeleteButton = document.getElementById('cancel-delete');
        const deletePasswordInput = document.getElementById('delete-password');

        // Show delete password modal
        deletePasswordOverlay.style.display = 'block';
        deletePasswordForm.style.display = 'block';

        confirmDeleteButton.addEventListener('click', function () {
            const password = deletePasswordInput.value;

            // Send password to server for verification
            fetch('/verify-delete-password', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    password: password,
                    commentId: commentElement.dataset.commentId, // Ensure you set commentId data attribute
                }),
            })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        // Password is correct, remove comment
                        commentElement.remove();
                        alert('댓글이 삭제되었습니다.');
                    } else {
                        alert('비밀번호가 올바르지 않습니다.');
                    }
                })
                .catch(error => {
                    console.error('댓글 삭제 실패:', error);
                })
                .finally(() => {
                    // Hide password modal
                    deletePasswordOverlay.style.display = 'none';
                    deletePasswordForm.style.display = 'none';
                    deletePasswordInput.value = '';
                });
        });

        cancelDeleteButton.addEventListener('click', function () {
            // Hide password modal
            deletePasswordOverlay.style.display = 'none';
            deletePasswordForm.style.display = 'none';
            deletePasswordInput.value = '';
        });
    }
});
