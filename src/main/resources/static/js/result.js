

$(document).ready(function(){
    $('select').on('change', function() {
        loadMVICFG(this.value);
        loadSummary(this.value);
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
      function loadSummary(version)
      {
          version=version.split('_');
          $.ajax({
              url: "mvicfg/summary/"+version[0]+"/"+version[1],
              type: "GET",
              async: false,
              success: function(result){
                  var ref=$("#summaryTable").find('tbody');
                  $("#verion1Locheader").html("LOC ("+result.version1Name+")");
                  $("#verion2Locheader").html("LOC ("+result.version2Name+")");
                  ref.empty();
                  var str='<tr>';

                  str+='<td>';
                  str+=result.version1Name+' - '+result.version2Name;
                  str+='</td>';

                  str+='<td>';
                  str+=result.version1Loc;
                  str+='</td>';

                  str+='<td>';
                  str+=result.version2Loc;
                  str+='</td>';

                  str+='<td>';
                  str+=result.churnRate;
                  str+='</td>';

                  str+='<td>';
                  str+="("+result.mvicfgNumNode+","+result.mvicfgNumEdge+")";
                  str+='</td>';

                  str+='<td>';
                  str+=result.mvicfgBuildTime;
                  str+='</td>';

                  str+='</tr>';
                  ref.append(str);

                  console.log(result);
            }});
        }
});
