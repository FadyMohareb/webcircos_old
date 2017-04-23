/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/* global React */

var CircosPanel = React.createClass({className: "circosPanel",
 save: function ()
    {
        //get svg element.
        var svg = document.getElementById("bioCircos");
        
        //get svg source.
        var serializer = new XMLSerializer();
        var source = serializer.serializeToString(svg);
        
        //add name spaces.
        if(!source.match(/^<svg[^>]+xmlns="http\:\/\/www\.w3\.org\/2000\/svg"/))
        {
            source = source.replace(/^<svg/, '<svg xmlns="http://www.w3.org/2000/svg"');
        }
        if(!source.match(/^<svg[^>]+"http\:\/\/www\.w3\.org\/1999\/xlink"/)){
            source = source.replace(/^<svg/, '<svg xmlns:xlink="http://www.w3.org/1999/xlink"');
        }
        
        //add xml declaration
        source = '<?xml version="1.0" standalone="no"?>\r\n' + source;

        //convert svg source to URI data scheme.
        var url = "data:image/svg+xml;charset=utf-8,"+encodeURIComponent(source);
        
        //create element a
        var a = document.createElement('a');
        
        //set url value to a element's href attribute.
        a.href=url;
        a.download='circos.svg';
        a.setAttribute('target', '_blank');
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
    },

    render: function () {

        return (React.createElement('div', {style: {height: "100vw"}},
//                React.createElement("form", {},
//                React.createElement("i", {className: "fa fa-floppy-o"})),
                React.createElement('button', {type: 'button', style: {float: 'right'}, className: 'btn btn-primary glyphicon glyphicon-save', onClick: this.save},React.createElement('strong', null, " Save")),
                React.createElement('br'),
                React.createElement('div', {id: "bioCircos", style: {height: 'inherit', width: 'inherit', margin: '8vw 20vw'}},
                                React.createElement('img', {src: "resources/emptyCircosV2.jpeg"}))));
    }
});

var renderCircosPanel = function () {
    React.render(
            React.createElement(CircosPanel),
            document.getElementById("circos")
            );
};
