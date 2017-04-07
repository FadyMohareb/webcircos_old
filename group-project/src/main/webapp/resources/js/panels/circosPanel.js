/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var CircosPanel = React.createClass({className: "circosPanel",
    doit: function doit() {
        renderCircos: function renderCircos(BioCircosGenome, ARC_01, HISTOGRAM01, LINE01, LINE02, HEATMAP01, HEATMAP02) {

            BioCircos01 = new BioCircos(ARC_01, HISTOGRAM01, LINE01, LINE02, HEATMAP01, HEATMAP02, BioCircosGenome, {// Initialize BioCircos.js with "BioCircosGenome" and Main configuration
                //Main configuration
                target: "testDiv", // Main configuration "target"
                svgWidth: 1350, // Main configuration "svgWidth"
                svgHeight: 900, // Main configuration "svgHeight"
                chrPad: 0.02, // Main configuration "chrPad"
                innerRadius: 246, // Main configuration "innerRadius"
                outerRadius: 270, // Main configuration "outerRadius"


                zoom: true,
                HISTOGRAMMouseEvent: true,
                HISTOGRAMMouseClickDisplay: false,
                HISTOGRAMMouseClickColor: "red", //"none","red"
                HISTOGRAMMouseClickOpacity: 1.0, //"none",1.0
                HISTOGRAMMouseClickStrokeColor: "none", //"none","#F26223"
                HISTOGRAMMouseClickStrokeWidth: "none", //"none",3
                HISTOGRAMMouseDownDisplay: false,
                HISTOGRAMMouseDownColor: "red", //"none","red"
                HISTOGRAMMouseDownOpacity: 1.0, //"none",1.0
                HISTOGRAMMouseDownStrokeColor: "none", //"none","#F26223"
                HISTOGRAMMouseDownStrokeWidth: "none", //"none",3
                HISTOGRAMMouseEnterDisplay: false,
                HISTOGRAMMouseEnterColor: "red", //"none","red"
                HISTOGRAMMouseEnterOpacity: 1.0, //"none",1.0
                HISTOGRAMMouseEnterStrokeColor: "none", //"none","#F26223"
                HISTOGRAMMouseEnterStrokeWidth: "none", //"none",3
                HISTOGRAMMouseLeaveDisplay: false,
                HISTOGRAMMouseLeaveColor: "red", //"none","red"
                HISTOGRAMMouseLeaveOpacity: 1.0, //"none",1.0
                HISTOGRAMMouseLeaveStrokeColor: "none", //"none","#F26223"
                HISTOGRAMMouseLeaveStrokeWidth: "none", //"none",3
                HISTOGRAMMouseMoveDisplay: false,
                HISTOGRAMMouseMoveColor: "red", //"none","red"
                HISTOGRAMMouseMoveOpacity: 1.0, //"none",1.0
                HISTOGRAMMouseMoveStrokeColor: "none", //"none","#F26223"
                HISTOGRAMMouseMoveStrokeWidth: "none", //"none",3
                HISTOGRAMMouseOutDisplay: true,
                HISTOGRAMMouseOutAnimationTime: 500,
                HISTOGRAMMouseOutColor: "red", //"none","red"
                HISTOGRAMMouseOutOpacity: 1.0, //"none",1.0
                HISTOGRAMMouseOutStrokeColor: "none", //"none","#F26223"
                HISTOGRAMMouseOutStrokeWidth: "none", //"none",3
                HISTOGRAMMouseUpDisplay: true,
                HISTOGRAMMouseUpColor: "red", //"none","red"
                HISTOGRAMMouseUpOpacity: 1.0, //"none",1.0
                HISTOGRAMMouseUpStrokeColor: "none", //"none","#F26223"
                HISTOGRAMMouseUpStrokeWidth: "none", //"none",3
                HISTOGRAMMouseOverDisplay: true,
                HISTOGRAMMouseOverColor: "red", //"none","red"
                HISTOGRAMMouseOverOpacity: 1.0, //"none",1.0
                HISTOGRAMMouseOverStrokeColor: "none", //"none","#F26223"
                HISTOGRAMMouseOverStrokeWidth: "none", //"none",3
                HISTOGRAMMouseOverTooltipsHtml01: "chr :",
                HISTOGRAMMouseOverTooltipsHtml02: "<br>position: ",
                HISTOGRAMMouseOverTooltipsHtml03: "-",
                HISTOGRAMMouseOverTooltipsHtml04: "<br>name : ",
                HISTOGRAMMouseOverTooltipsHtml05: "<br>value : ",
                HISTOGRAMMouseOverTooltipsHtml06: "",
                HISTOGRAMMouseOverTooltipsPosition: "absolute",
                HISTOGRAMMouseOverTooltipsBackgroundColor: "white",
                HISTOGRAMMouseOverTooltipsBorderStyle: "solid",
                HISTOGRAMMouseOverTooltipsBorderWidth: 0,
                HISTOGRAMMouseOverTooltipsPadding: "3px",
                HISTOGRAMMouseOverTooltipsBorderRadius: "3px",
                HISTOGRAMMouseOverTooltipsOpacity: 0.8,
                ARCMouseEvent: true,
                ARCMouseClickDisplay: true,
                ARCMouseClickColor: "red",
                ARCMouseClickArcOpacity: 1.0,
                ARCMouseClickArcStrokeColor: "#F26223",
                ARCMouseClickArcStrokeWidth: 1,
                ARCMouseClickTextFromData: "fourth",
                ARCMouseClickTextOpacity: 1,
                ARCMouseClickTextColor: "red",
                ARCMouseClickTextSize: 8,
                ARCMouseClickTextPostionX: 0,
                ARCMouseClickTextPostionY: 0,
                ARCMouseClickTextDrag: true,
                ARCMouseDownDisplay: false,
                ARCMouseDownColor: "green",
                ARCMouseDownArcOpacity: 1.0,
                ARCMouseDownArcStrokeColor: "#F26223",
                ARCMouseDownArcStrokeWidth: 0,
                ARCMouseEnterDisplay: false,
                ARCMouseEnterColor: "yellow",
                ARCMouseEnterArcOpacity: 1.0,
                ARCMouseEnterArcStrokeColor: "#F26223",
                ARCMouseEnterArcStrokeWidth: 0,
                ARCMouseLeaveDisplay: false,
                ARCMouseLeaveColor: "pink",
                ARCMouseLeaveArcOpacity: 1.0,
                ARCMouseLeaveArcStrokeColor: "#F26223",
                ARCMouseLeaveArcStrokeWidth: 0,
                ARCMouseMoveDisplay: false,
                ARCMouseMoveColor: "red",
                ARCMouseMoveArcOpacity: 1.0,
                ARCMouseMoveArcStrokeColor: "#F26223",
                ARCMouseMoveArcStrokeWidth: 0,
                ARCMouseOutDisplay: true,
                ARCMouseOutAnimationTime: 500,
                ARCMouseOutColor: "red",
                ARCMouseOutArcOpacity: 1.0,
                ARCMouseOutArcStrokeColor: "red",
                ARCMouseOutArcStrokeWidth: 0,
                ARCMouseUpDisplay: false,
                ARCMouseUpColor: "grey",
                ARCMouseUpArcOpacity: 1.0,
                ARCMouseUpArcStrokeColor: "#F26223",
                ARCMouseUpArcStrokeWidth: 0,
                ARCMouseOverDisplay: true,
                ARCMouseOverColor: "red",
                ARCMouseOverArcOpacity: 1.0,
                ARCMouseOverArcStrokeColor: "#F26223",
                ARCMouseOverArcStrokeWidth: 3,
                ARCMouseOverTooltipsHtml01: "item : ",
                ARCMouseOverTooltipsHtml02: "chr : ",
                ARCMouseOverTooltipsHtml03: "<br>end : ",
                ARCMouseOverTooltipsHtml04: "<br>des : ",
                ARCMouseOverTooltipsHtml05: "",
                ARCMouseOverTooltipsPosition: "absolute",
                ARCMouseOverTooltipsBackgroundColor: "white",
                ARCMouseOverTooltipsBorderStyle: "solid",
                ARCMouseOverTooltipsBorderWidth: 0,
                ARCMouseOverTooltipsPadding: "3px",
                ARCMouseOverTooltipsBorderRadius: "3px",
                ARCMouseOverTooltipsOpacity: 0.8,
                LINEMouseEvent: true,
                LINEMouseClickDisplay: false,
                LINEMouseClickLineOpacity: 1, //"none"
                LINEMouseClickLineStrokeColor: "red", //"none"
                LINEMouseClickLineStrokeWidth: "none", //"none"
                LINEMouseDownDisplay: false,
                LINEMouseDownLineOpacity: 1, //"none"
                LINEMouseDownLineStrokeColor: "red", //"none"
                LINEMouseDownLineStrokeWidth: "none", //"none"
                LINEMouseEnterDisplay: false,
                LINEMouseEnterLineOpacity: 1, //"none"
                LINEMouseEnterLineStrokeColor: "red", //"none"
                LINEMouseEnterLineStrokeWidth: "none", //"none"
                LINEMouseLeaveDisplay: false,
                LINEMouseLeaveLineOpacity: 1, //"none"
                LINEMouseLeaveLineStrokeColor: "red", //"none"
                LINEMouseLeaveLineStrokeWidth: "none", //"none"
                LINEMouseMoveDisplay: false,
                LINEMouseMoveLineOpacity: 1, //"none"
                LINEMouseMoveLineStrokeColor: "red", //"none"
                LINEMouseMoveLineStrokeWidth: "none", //"none"
                LINEMouseOutDisplay: false,
                LINEMouseOutAnimationTime: 500,
                LINEMouseOutLineOpacity: 1.0, //"none"
                LINEMouseOutLineStrokeColor: "red", //"none"
                LINEMouseOutLineStrokeWidth: "none", //"none"
                LINEMouseUpDisplay: true,
                LINEMouseUpLineOpacity: 1, //"none"
                LINEMouseUpLineStrokeColor: "red", //"none"
                LINEMouseUpLineStrokeWidth: "none", //"none"
                LINEMouseOverDisplay: true,
                LINEMouseOverLineOpacity: 1, //"none"
                LINEMouseOverLineStrokeColor: "red", //"none"
                LINEMouseOverLineStrokeWidth: "none", //"none"
                LINEMouseOverTooltipsHtml01: "Line",
                LINEMouseOverTooltipsPosition: "absolute",
                LINEMouseOverTooltipsBackgroundColor: "white",
                LINEMouseOverTooltipsBorderStyle: "solid",
                LINEMouseOverTooltipsBorderWidth: 0,
                LINEMouseOverTooltipsPadding: "3px",
                LINEMouseOverTooltipsBorderRadius: "3px",
                LINEMouseOverTooltipsOpacity: 0.8,
                HEATMAPMouseEvent: true,
                HEATMAPMouseClickDisplay: true,
                HEATMAPMouseClickOpacity: 1.0, //"none",1.0
                HEATMAPMouseDownDisplay: false,
                HEATMAPMouseDownOpacity: 1.0, //"none",1.0
                HEATMAPMouseDownStrokeWidth: "none", //"none",3
                HEATMAPMouseEnterDisplay: false,
                HEATMAPMouseEnterColor: "green", //"none","red"
                HEATMAPMouseEnterOpacity: 1.0, //"none",1.0
                HEATMAPMouseEnterStrokeWidth: "none", //"none",3
                HEATMAPMouseLeaveDisplay: false,
                HEATMAPMouseLeaveColor: "none",
                HEATMAPMouseLeaveOpacity: 1.0, //"none",1.0
                HEATMAPMouseLeaveStrokeWidth: "none", //"none",3
                HEATMAPMouseMoveDisplay: true,
                HEATMAPMouseMoveOpacity: 1.0, //"none",1.0
                HEATMAPMouseMoveStrokeWidth: "none", //"none",3
                HEATMAPMouseOutDisplay: true,
                HEATMAPMouseOutAnimationTime: 500,
                HEATMAPMouseOutColor: "none",
                HEATMAPMouseOutOpacity: 1.0, //"none",1.0
                HEATMAPMouseOutStrokeWidth: "none", //"none",3
                HEATMAPMouseUpDisplay: false,
                HEATMAPMouseUpOpacity: 1.0, //"none",1.0
                HEATMAPMouseUpStrokeWidth: "none", //"none",3
                HEATMAPMouseOverDisplay: true,
                HEATMAPMouseUpColor: "none", //"none","red"
                HEATMAPMouseOverColor: "none",
                HEATMAPMouseOverOpacity: 1.0, //"none",1.0
                HEATMAPMouseOverStrokeWidth: "none", //"none",3
                HEATMAPMouseOverTooltipsHtml01: "chr : ",
                HEATMAPMouseOverTooltipsHtml02: "<br>position: ",
                HEATMAPMouseOverTooltipsHtml03: "-",
                HEATMAPMouseOverTooltipsHtml04: "<br>name : ",
                HEATMAPMouseOverTooltipsHtml05: "<br>value : ",
                HEATMAPMouseOverTooltipsHtml06: "",
                HEATMAPMouseOverTooltipsPosition: "absolute",
                HEATMAPMouseOverTooltipsBackgroundColor: "white",
                HEATMAPMouseOverTooltipsBorderStyle: "solid",
                HEATMAPMouseOverTooltipsBorderWidth: 0,
                HEATMAPMouseOverTooltipsPadding: "3px",
                HEATMAPMouseOverTooltipsBorderRadius: "3px",
                HEATMAPMouseOverTooltipsOpacity: 0.8
            });
            BioCircos01.draw_genome(BioCircos01.genomeLength); // BioCircos.js callback

        }

        var circosInput = {};
        circosInput.something = "something"
        $.ajax({
            url: "/circos.data",
            dataType: 'json',
            type: 'POST',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(circosInput),
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

        return;
    },
    render: function () {

        return (React.createElement("div", {className: "panel panel-primary"},
                React.createElement('div', {className: "panel-heading"}, "Circos"),
                React.createElement('div', {className: "panel-body"},
                        React.createElement('div', {id: "testDiv"})),
                React.createElement('button', {type: 'button', className: 'btn btn-primary', onClick: this.doit},
                        'Render circos'))
                );

    }
})

var renderCircosPanel = function () {
    React.render(
            React.createElement(CircosPanel),
            document.getElementById("centerContainer")
            );
};