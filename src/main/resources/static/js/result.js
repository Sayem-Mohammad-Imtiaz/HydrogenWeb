

$(document).ready(function(){
    $('select').on('change', function() {
        loadMVICFG(this.value);
    });

    function loadMVICFG(version)
    {
        $.ajax({
            url: "mvicfg/json/"+version,
            type: "GET",
            async: false,
            success: function(result){
                d3.select("#graph").graphviz().width(1380).height(500).
                    renderDot(result).fit(true);
          }});
      }
});
