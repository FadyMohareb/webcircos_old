/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var FilesParentPoolPanel = React.createClass({className: "FilesGeneralPanel",
    
    render: function () {

        return (
                React.createElement('div', {id: "blabla"}),
                        React.createElement('button', {className: 'btn btn-primary'},
                                'Parent-pool tab test')
                );


    }
})

var renderFilesParentPoolPanel = function () {
    React.render(
            React.createElement(FilesParentPoolPanel),
            document.getElementById("filesParentPool")
            );
};