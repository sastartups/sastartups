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

