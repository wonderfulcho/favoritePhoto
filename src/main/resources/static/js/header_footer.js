document.addEventListener('DOMContentLoaded', function() {
    // 메뉴버튼 토글 기능
    const menu = document.getElementById('menu');
    const menuBtn = document.getElementById('headerMenuBtn');

    function menuClick() {
         if (menu.style.display === 'none' || menu.style.display === '') {
            menu.style.display = 'block';
         } else {
            menu.style.display = 'none';
         }
    }
    menuBtn.addEventListener('click', menuClick);

    // 서치버튼 토글 기능
    const search = document.getElementById('search');
    const searchBtn = document.getElementById('headerSearchBtn');

    function searchClick() {
        if (search.style.display === 'none' || search.style.display === '') {
            search.style.display = 'block';
        } else {
            search.style.display = 'none';
        }
    }
    searchBtn.addEventListener('click', searchClick);
});