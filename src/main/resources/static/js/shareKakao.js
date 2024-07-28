document.addEventListener('DOMContentLoaded', function() {
    if(!window.KakaoInitialized) {
        initializeKakao();
        window.KakaoInitialized = true;
    }

    function initializeKakao() {
        $.ajax({
            type: 'POST',
            url: '/getJSKey',
            success: function (kakaoJSKey) {
                if (kakaoJSKey) {
                    Kakao.init(kakaoJSKey);
                    shareMessage();
                }
            },
            error: function(xhr, status, error) {
                console.log('error', status, error);
            }
        });
    }

    function shareMessage() {
        const shareUrl = document.getElementById("linkInput").value;

        Kakao.Share.createDefaultButton({
            container: '#kakaoShareBtn',
            objectType: 'feed',
            content: {
                title: 'Im Greet',
                description: title,
                imageUrl: image,
                link: {
                    mobileWebUrl: shareUrl,
                    webUrl: shareUrl
                },
            },
            buttons: [
                {
                    title: '웹으로 보기',
                    link: {
                        mobileWebUrl: shareUrl,
                        webUrl: shareUrl,
                    },
                }
            ],
            installTalk: true
        });
    }

});

