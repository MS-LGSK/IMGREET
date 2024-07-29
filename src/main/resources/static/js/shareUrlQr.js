function generateQRCode() {
    const shareUrl = document.getElementById("linkInput").value;
    $('#shareQRContainer').empty();
    $('#shareQRContainer').qrcode({width: 128, height: 128, text: shareUrl});
    $('#qrPopup').show();
    $('#qrPopupOverlay').show();
}

document.getElementById('qrShareBtn').addEventListener('click', generateQRCode);
document.getElementById('qrPopupClose').addEventListener('click', function() {
    $('#qrPopup').hide();
    $('#qrPopupOverlay').hide();
});