/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var CircosPanel = React.createClass({className: "circosPanel",
    sendData: function(){
        alert(JSON.stringify(Structure));
         $.ajax({
            url: "/circos.data",
            dataType: 'json',
            type: 'POST',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(Structure),
            success: function (data) {
                var ARC_01;
                var HISTOGRAM01;
                var LINE01;
                var LINE02;
                var HEATMAP01;
                var HEATMAP02;
                console.log(data);

                if (data.histo !== null) {
                    HISTOGRAM01 = [data.histo.histId, data.histo.properties, data.histo.histDataPoint];
                } else {
                    HISTOGRAM01 = [];
                }

                if (data.arc !== null) {
                    ARC_01 = [data.arc.indGffid, data.arc.properties, data.arc.gffDataPoint];
                } else {
                    ARC_01 = [];
                }

                if (data.genomicCoverage !== null) {
                    LINE01 = [data.genomicCoverage.lineId, data.genomicCoverage.properties, data.genomicCoverage.linePoints];
                } else {
                    LINE01 = [];
                }

                if (data.transcriptomicCoverage !== null) {

                    LINE02 = [data.transcriptomicCoverage.lineId, data.transcriptomicCoverage.properties, data.transcriptomicCoverage.linePoints];
                } else {
                    LINE02 = [];
                }

                if (data.dEHeatMap !== null) {
                    HEATMAP01 = [data.dEHeatMap.heatMapId, data.dEHeatMap.properties, data.dEHeatMap.heatMapDataPoint];
                } else {
                    HEATMAP01 = [];
                }

                if (data.geneExpressionHeatMap !== null) {
                    HEATMAP02 = [data.geneExpressionHeatMap.heatMapId, data.geneExpressionHeatMap.properties, data.geneExpressionHeatMap.heatMapDataPoint];
                } else {
                    HEATMAP02 = [];
                }
                
                renderCircos(data.genomes, ARC_01, HISTOGRAM01, LINE01, LINE02, HEATMAP01, HEATMAP02);
            },
            error: function () {
                alert("Wrong data");
                console.error(status, err.toString());
            }

        });
        
    },
    render: function () {

        return (
                React.createElement('div', {id: "blabla"},
         React.createElement('button', {className: 'btn btn-primary', onClick: this.sendData},
                                'Mateuszek'))
                        
                );


    }
})

var renderCircosPanel = function () {
    React.render(
            React.createElement(CircosPanel),
            document.getElementById("circos")
            );
};
