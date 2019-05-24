document.getElementById('showmore').addEventListener('click', showmore);
document.getElementById('showless').addEventListener('click', showless);

function showmore(){

    document.getElementById('para1').classList.remove('texthide');
    document.getElementById('para1').className = 'textshow';
    document.getElementById('showmore').className = 'hidden';
    document.getElementById('showless').className = 'show';

}

function showless(){
    document.getElementById('para1').classList.remove('textshow');
    document.getElementById('para1').className = 'texthide';
    document.getElementById('showless').className = 'hidden';
    document.getElementById('showmore').className = 'show';
}

