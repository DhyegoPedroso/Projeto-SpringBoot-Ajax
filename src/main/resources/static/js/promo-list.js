// efeito infinite scroll
$(window).scroll(function () {
   
   var scrollTop = $(this).scrollTop();
   var conteudo = $(document).height() - $(window).height();
   
    console.log('scroll: ', scrollTop, ' | ', 'conteudo', conteudo);
    
    if (scrollTop >= conteudo) {
        console.log("***");
    }
    
});