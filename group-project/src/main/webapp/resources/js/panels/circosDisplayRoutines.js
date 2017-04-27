function renderCircos(height, width, BioCircosGenome, ARC_01, HISTOGRAM01, LINE01, LINE02, HEATMAP01, HEATMAP02, BACKGROUND01, BACKGROUND02) {

    BioCircos01 = new BioCircos(height, width, ARC_01, HISTOGRAM01, LINE01, LINE02, HEATMAP01, HEATMAP02, BACKGROUND01, BACKGROUND02, BioCircosGenome, {// Initialize BioCircos.js with "BioCircosGenome" and Main configuration
        //Main configuration
        target: 'bioCircos', // Main configuration "target"
        svgWidth: 1000,//width, // Main configuration "svgWidth"
        svgHeight: 1000,//height, // Main configuration "svgHeight"
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
        HISTOGRAMMouseOverTooltipsHtml06: "<br>snpCount : ",
        HISTOGRAMMouseOverTooltipsHtml07: " ",
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
        ARCMouseOverTooltipsHtml01: "chr : ",
        ARCMouseOverTooltipsHtml02: "<br>start : ",
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
        LINEMouseOutDisplay: true,
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
        LINEMouseOverTooltipsHtml01: "Coverage",
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
        HEATMAPMouseOverTooltipsOpacity: 0.8,
        nomeMouseEvent: true,
        GenomeMouseOverDisplay: true,
        GenomeMouseOverColor: "none",
        GenomeMouseOverOpacity: 1.0,
        GenomeMouseOverStrokeWidth: 3,
        GenomeMouseOverStrokeColor: "#F26223",
        GenomeMouseOverTooltipsHtml01: "Length : ",
        GenomeMouseOverTooltipsPosition: "absolute",
        GenomeMouseOverTooltipsBackgroundColor: "red",
        GenomeMouseOverTooltipsBorderStyle: "solid",
        GenomeMouseOverTooltipsBorderWidth: 0,
        GenomeMouseOverTooltipsPadding: "3px",
        GenomeMouseOverTooltipsBorderRadius: "3px",
        GenomeMouseOverTooltipsOpacity: 0.8,
        GenomeMouseOutDisplay: true,
        GenomeMouseOutAnimationTime: 500,
        GenomeMouseOutColor: "red",
        GenomeMouseOutGenomeOpacity: 1.0,
        GenomeMouseOutGenomeStrokeColor: "none",
        GenomeMouseOutGenomeStrokeWidth: "none"
    });
   
    BioCircos01.draw_genome(BioCircos01.genomeLength); // BioCircos.js callback
}

function renderBSA(BioCircosGenome, LINK01) {
    //commented out 
    BioCircos02 = new BioCircos(LINK01, BioCircosGenome, {// Initialize BioCircos.js with "BioCircosGenome" and Main configuration
        //Main configuration
        target: "bsaCircosTemp", // Main configuration "target"
        svgWidth: 1350, // Main configuration "svgWidth"
        svgHeight: 900, // Main configuration "svgHeight"
        chrPad: 0.04, // Main configuration "chrPad"
        innerRadius: 246, // Main configuration "innerRadius"
        outerRadius: 270, // Main configuration "outerRadius"
        BSAzoom: true

    });
    
    BioCircos02.BSA(BioCircosGenome, 4); // BioCircos.js callback
    $('#bsaCircos').html($('#bsaCircosTemp').html());
}
