window.setInterval(function(){
    if(document.documentElement["clientWidth"],
        document.body["scrollWidth"],
        document.documentElement["scrollWidth"],
        document.body["offsetWidth"],
    document.documentElement["offsetWidth"] >= 767){
        window.onscroll = function() {animation(), animation2(), animation3()};
        document.getElementById('para1').className = 'para1';
    } else {
        window.onscroll = function() {};
        document.getElementById('para1').classList.remove('para1');
        document.getElementById('para1').className = 'para2';
        document.getElementById("element1").classList.remove("hidden");
        document.getElementById("element3").classList.remove("hidden");
    }
}, 300);



function animation(){
    if (document.body.scrollTop > 300 || document.documentElement.scrollTop > 200) {
        document.getElementById("element1").classList.remove("hidden");
        document.getElementById("element1").className = "show";
    }
}

function animation2() {
    if (document.body.scrollTop > 800 || document.documentElement.scrollTop > 700) {
        document.getElementById("element2").classList.remove("hidden");
        document.getElementById("element2").className = "show";
    }
}

function animation3() {
    if (document.body.scrollTop > 1200 || document.documentElement.scrollTop > 1100) {
        document.getElementById("element3").classList.remove("hidden");
        document.getElementById("element3").className = "show";
    }
}



// text animation

anime.timeline({loop: true})
    .add({
        targets: '.ml5 .line',
        opacity: [0.5,1],
        scaleX: [0, 1],
        easing: "easeInOutExpo",
        duration: 700
    }).add({
    targets: '.ml5 .line',
    duration: 600,
    easing: "easeOutExpo",
    translateY: function(e, i, l) {
        var offset = -0.625 + 0.625*2*i;
        return offset + "em";
    }
}).add({
    targets: '.ml5 .ampersand',
    opacity: [0,1],
    scaleY: [0.5, 1],
    easing: "easeOutExpo",
    duration: 600,
    offset: '-=600'
}).add({
    targets: '.ml5 .letters-left',
    opacity: [0,1],
    translateX: ["0.5em", 0],
    easing: "easeOutExpo",
    duration: 600,
    offset: '-=300'
}).add({
    targets: '.ml5 .letters-right',
    opacity: [0,1],
    translateX: ["-0.5em", 0],
    easing: "easeOutExpo",
    duration: 600,
    offset: '-=600'
}).add({
    targets: '.ml5',
    opacity: 0,
    duration: 1000,
    easing: "easeOutExpo",
    delay: 10000
});

