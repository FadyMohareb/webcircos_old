/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var CircosPanel = React.createClass({className: "circosPanel",
    doit: function doit() {
        renderCircos: function renderCircos(BioCircosGenome, ARC_01) {

            BioCircos01 = new BioCircos(ARC_01, BioCircosGenome, {// Initialize BioCircos.js with "BioCircosGenome" and Main configuration
                //Main configuration
                target: "testDiv", // Main configuration "target"
                svgWidth: 490, // Main configuration "svgWidth"
                svgHeight: 490, // Main configuration "svgHeight"
                chrPad: 0.02, // Main configuration "chrPad"
                innerRadius: 180, // Main configuration "innerRadius"
                outerRadius: 200, // Main configuration "outerRadius"

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
                console.log(data)
//                var HISTOGRAM01 = [data.histo.histId, data.histo.properties, data.histo.histDataPoint]
                var ARC_01 = [data.arc.indGffid, data.arc.properties, data.arc.gffDataPoint]
               
                console.log(ARC_01)
                renderCircos(data.genomes, ARC_01)
            },
            error: function (xhr, status, err) {
                alert("Wrong data");
                console.error(status, err.toString());
            }

        });
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