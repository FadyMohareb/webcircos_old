/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var PlotSpace = React.createClass({
    handleSaving: function () {
        alert("This button will log in the user soon :)")
    },
        render: function () {
        
        return (React.createElement("div", {className: "panel panel-primary"},
                React.createElement('div', {className: "panel-heading"}, "Circos"),
                    React.createElement('div', {className: "panel-body"}, 
                        React.createElement("image", {src: "http://veronicaraulin.com/wp-content/uploads/2015/10/cropped-bigstock-Loading-Bar-With-Bulb-Creativ-94955525.jpg",
                        class: "img-responsive", width: 750, height: 650})
            ))
          );
    
    }
})

var renderPlotSpace = function () {
    React.render(
        React.createElement(PlotSpace),
        document.getElementById("centerContainer")
    );
};