/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var CircosPanel = React.createClass({className: "circosPanel",
 
    render: function () {

        return (
                React.createElement('div', {id: "bioCircos"})
                        
                );


    }
})

var renderCircosPanel = function () {
    React.render(
            React.createElement(CircosPanel),
            document.getElementById("circos")
            );
};
