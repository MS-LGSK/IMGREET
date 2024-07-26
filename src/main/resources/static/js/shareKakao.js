document.addEventListener('DOMContentLoaded', function() {
    if(!window.KakaoInitialized) {
        initializeKakao();
        window.KakaoInitialized = true;
    }

    function initializeKakao() {
        Kakao.init('키 값'); // 키값 숨기기
        console.log(Kakao.isInitialized());

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
        document.querySelector('.kakao-icon').addEventListener('click', shareMessage);

    }
});

