
document.getElementById("picture").addEventListener("click", function () {
    var client = filestack.init('ANjhOz5feQ0OWiw2r1EEgz');
    client.picker({
        onFileUploadFinished: function(response){
            this.value ="https://cdn.filestackcontent.com/" + (response.handle);
            document.getElementById("profilePicture").value = this.value
        }
    }).open();
});

document.getElementById("picture2").addEventListener("click", function () {
    var client = filestack.init('ANjhOz5feQ0OWiw2r1EEgz');
    client.picker({
        onFileUploadFinished: function(response){
            this.value ="https://cdn.filestackcontent.com/" + (response.handle);
            document.getElementById("coverPicture").value = this.value
        }
    }).open();
});
