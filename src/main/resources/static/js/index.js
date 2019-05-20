window.setInterval(function(){
    if(document.documentElement["clientWidth"],
        document.body["scrollWidth"],
        document.documentElement["scrollWidth"],
        document.body["offsetWidth"],
    document.documentElement["offsetWidth"] >= 767){
        window.onscroll = function() {animation(), animation2(), animation3()};
    } else {
        window.onscroll = function() {animation(), animation2(), animation3()};
    }
}, 300);

function animation(){
    if (document.body.scrollTop > 300 || document.documentElement.scrollTop > 300) {
        document.getElementById("element1").classList.remove("hidden");
        document.getElementById("element1").className = "left";
    }
}

function animation2() {
    if (document.body.scrollTop > 800 || document.documentElement.scrollTop > 800) {
        document.getElementById("element2").classList.remove("hidden");
        document.getElementById("element2").className = "right";
    }
}

function animation3() {
    if (document.body.scrollTop > 1200 || document.documentElement.scrollTop > 1200) {
        document.getElementById("element3").classList.remove("hidden");
        document.getElementById("element3").className = "left";
    }
}

