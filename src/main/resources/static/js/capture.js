// capture.js
function captureCanvasAsImage() {
    return new Promise((resolve, reject) => {
        const canvas = document.getElementById('myCanvas');
        if (!canvas) {
            return reject('myCanvas 요소를 찾을 수 없습니다.');
        }

        const images = canvas.getElementsByTagName('img');
        let loadedImages = 0;
        const totalImages = images.length;

        function checkAllImagesLoaded() {
            loadedImages++;
            if (loadedImages === totalImages) {
                html2canvas(canvas)
                    .then(canvas => {
                        const imageDataUrl = canvas.toDataURL("image/png");
                        resolve(imageDataUrl);
                    })
                    .catch(error => {
                        reject(error);
                    });
            }
        }

        if (totalImages === 0) {
            // 이미지가 없는 경우 바로 캡처 진행
            html2canvas(canvas)
                .then(canvas => {
                    const imageDataUrl = canvas.toDataURL("image/png");
                    resolve(imageDataUrl);
                })
                .catch(error => {
                    reject(error);
                });
        } else {
            // 이미지 로드 이벤트 리스너 설정
            for (let img of images) {
                if (img.complete) {
                    checkAllImagesLoaded();
                } else {
                    img.addEventListener('load', checkAllImagesLoaded);
                    img.addEventListener('error', () => reject('이미지 로드 오류'));
                }
            }
        }
    });
}
