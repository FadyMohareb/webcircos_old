
var ParentPoolPanel = React.createClass({className: "ParentPoolPanel",
    save: function ()
    {
        //get svg element.
        var svg = document.getElementById("bsaCircos");
        
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
        a.download='bsaCircos.svg';
        a.setAttribute('target', '_blank');
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
    },    
    
    render: function () {

        return (
                React.createElement('div', null,
                        React.createElement('button', {type: 'button', 
                            className: 'btn btn-primary glyphicon glyphicon-save', style: {float: 'right'}, onClick: this.save},React.createElement('strong', null, " Save")),
                React.createElement('br'),
                                React.createElement('div', {id: "bsaCircos", style: {height: 'inherit', width: 'inherit', margin: '8vw 20vw'}}))
                );
    }
})

var renderFilesParentPoolPanel = function () {
    React.render(
            React.createElement(ParentPoolPanel),
            document.getElementById("parentPool")
            );
};