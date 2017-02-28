(
// RUN THIS BLOCK FIRST  (note this boots server)
// ... update the following to local path of superstudio.sc
("/Users/randallwest/code/mirrorecho/superstudio/ss.sc").loadPaths;
)





(
// RUN THIS NEXT TO LOAD COMMON MODULES + WORK-SPECIFIC STUFF
// (rerun after stopping sound with CMD-PERIOD ... )
~ss.loadCommon({
	SynthDef( "semi.tap", { | freq=440, t_amp | // NOTE: t_amp = amp made as a TrigControl
		var sig;


		sig = Resonz.ar(
			WhiteNoise.ar(70!2) * Decay2.kr( t_amp, 0.002, 0.1 ),
			freq,
			0.02,
			4
		).distort * 0.4;

		Out.ar( ~ss.bus.master, sig );
		DetectSilence.ar( sig, doneAction: 2 );
	}).add;
	/*
	// MIDI: simple defs for midi messaging
	~ss.load(["midi"], {
		~ss.midi.instrument = "ss.pop"; // <- sets midi instrument (default is "ss.spacey");
	});
	*/

	// BUFFERS / SOUND LIBRARY:
	~ss.buf.libraryPath = "/Users/randallwest/Echo/Sound/Library/";
	~ss.buf.loadLibrary("japan-cicadas");

});
)



(

var pattern, stream;
pattern = Pbind(
	\xyx, Pseq([1,2,3])
);
stream = pattern.asStream;
4.do({ stream.next(Event.new).postln; });

)

(
var pattern, stream;
pattern = Pseq([
	Pbind( \abc, Pseq([1,2,3]) ),
	Pbind( \def, Pseq([4,5,6]) ),
	Pbind( \xyz, Pseq([7,8,9]) )
]);
stream = pattern.asStream;
stream.postln;
10.do({ stream.next(Event.new).postln });
)





// ~ss.buf.drone("japan-cicadas", "DR0000_0192");

(
var ref_pitch = 60.midicps;

p = Pbind(
	*[
		instrument: "semi.tap",
		dur: 1/8,
		freq: Pseq([ ref_pitch*17/15*2, ref_pitch, ref_pitch, ref_pitch*16/15],24),
		amp: Prand([0.2,0.3,0.4,0.8], inf),
	]
);
)

p.play;




(
SynthDef( \help_SPE3_Mridangam, { arg t_amp=0.2;
    var out;

    out = Resonz.ar(
        WhiteNoise.ar(70) * Decay2.kr( t_amp, 0.002, 0.1 ),
        60.midicps,
        0.02,
        4
    ).distort * 0.4;

    Out.ar( 0, out );
    DetectSilence.ar( out, doneAction: 2 );
}).add;
)