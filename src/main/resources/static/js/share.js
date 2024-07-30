document.getElementById("detailButton").addEventListener("click", function () {
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
