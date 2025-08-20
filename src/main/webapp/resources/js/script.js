hs.graphicsDir = '../resources/css/graphics/';
hs.wrapperClassName = "controls-in-heading";
hs.outlineType = 'rounded-white';
hs.align = 'center';
hs.transitions = ['expand','crossfade'];

hs.addSlideshow({

    interval:1000,
    repeat:true,
    useControls:true,
    overlayOptions:{
        opacity:0.75,
        position:'top right',
        hideOnMouseOut:false,
    },
    thumbstrip: {
        position:'bottom center',
        mode:"horizontal"
    }

});