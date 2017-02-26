(
// RUN THIS BLOCK FIRST  (note this boots server)
// ... update the following to local path of superstudio.sc
("/Users/randallwest/code/mirrorecho/superstudio/ss.sc").loadPaths;
)



(
// RUN THIS NEXT TO LOAD COMMON MODULES + WORK-SPECIFIC STUFF
// (rerun after stopping sound with CMD-PERIOD ... )
~ss.loadCommon({
	SynthDef( "semi.tap", { arg t_amp=0.2, freq=440;
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






// ~ss.buf.drone("japan-cicadas", "DR0000_0192");

(
var ref_pitch = 49.midicps;

p = Pbind(
	*[
		instrument: "semi.tap",
		dur: 1/8,
		freq: Pseq([ref_pitch*15/16, ref_pitch*16/15, ref_pitch, ref_pitch, ref_pitch*16/15],24),
		amp:0.2,
	]
);
)

p.play;

60!2;




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