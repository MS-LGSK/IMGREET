async function captureCanvasAsImage() {
    const canvas = document.getElementById('myCanvas');
    if (!canvas) {
        throw new Error('myCanvas 요소를 찾을 수 없습니다.');
    }

    try {
        const capturedCanvas = await html2canvas(canvas);
        return capturedCanvas.toDataURL("image/png");
    } catch (error) {
        throw new Error('html2canvas 호출 오류: ' + error.message);
    }
}