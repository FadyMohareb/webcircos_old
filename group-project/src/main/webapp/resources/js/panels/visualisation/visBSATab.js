
var VisBSATab = React.createClass({className: "visBsaTab",
    save: function ()
    {
        //get svg element.
        var svg = document.getElementById("bsaCircos");

        //get svg source.
        var serializer = new XMLSerializer();
        var source = serializer.serializeToString(svg);

        //add name spaces.
        if (!source.match(/^<svg[^>]+xmlns="http\:\/\/www\.w3\.org\/2000\/svg"/))
        {
            source = source.replace(/^<svg/, '<svg xmlns="http://www.w3.org/2000/svg"');
        }
        if (!source.match(/^<svg[^>]+"http\:\/\/www\.w3\.org\/1999\/xlink"/)) {
            source = source.replace(/^<svg/, '<svg xmlns:xlink="http://www.w3.org/1999/xlink"');
        }

        //add xml declaration
        source = '<?xml version="1.0" standalone="no"?>\r\n' + source;

        //convert svg source to URI data scheme.
        var url = "data:image/svg+xml;charset=utf-8," + encodeURIComponent(source);

        //create element a
        var a = document.createElement('a');

        //set url value to a element's href attribute.
        a.href = url;
        a.download = 'bsaCircos.svg';
        a.setAttribute('target', '_blank');
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
    },

    render: function () {
        $("#centerContainer").height($("#leftCol").height());
        return (React.createElement('div', {className: 'container', style: {width: 'inherit'}},
                React.createElement('button', {type: 'button', style: {float: 'right'}, className: 'btn btn-primary glyphicon glyphicon-save', onClick: this.save}, React.createElement('strong', null, " Save")),
                React.createElement('br'),
                React.createElement('div', {id: "bsaCircos", style: {height: '90%', width: '100%', 'text-align': "center"}},
                        React.createElement('img', {src: "resources/EmptyBSA.PNG", style: {height: "1000px", width: "1150px"}}))));
    }
});
