(
// RUN THIS BLOCK FIRST  (note this boots server)
// ... update the following to local path of superstudio.sc
("/Users/randallwest/code/mirrorecho/superstudio/ss.sc").loadPaths;
)

~ss.projectPath;

(
// RUN THIS NEXT TO LOAD COMMON MODULES + WORK-SPECIFIC STUFF
// (rerun after stopping sound with CMD-PERIOD ... )
~ss.loadCommon({
	// ~ss.load(["synth.library"]);
	~ss.projectPath = "".resolveRelative;
	(~ss.projectPath ++ "synths.sc").loadPaths;

	/*
	// MIDI: simple defs for midi messaging
	~ss.load(["midi"], {
		~ss.midi.instrument = "ss.pop"; // <- sets midi instrument (default is "ss.spacey");
	});
	*/

	// BUFFERS / SOUND LIBRARY:
	~ss.buf.libraryPath = "/Users/randallwest/Echo/Sound/Library/";
	~ss.buf.loadLibrary("japan-cicadas");
	~ss.buf.loadLibrary("shamisen");
	~ss.buf.loadLibrary("plucky");
});
)

c = ~ss.buf.play("japan-cicadas", "0192-insects-honen-in-temple", ['start',0] );
c = ~ss.buf.play("japan-cicadas", "0191-insects-temple", ['start',7]);
c.free;

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

~ss.buf['plucky']['E3-glass-pizz-1'].play;

BufSampleRate.ir(~ss.buf['japan-cicadas']['0191-insects-temple']).poll;


// ~ss.buf.drone("japan-cicadas", "DR0000_0192");


(
p = Pbind(
	*[
		instrument: "semi.tap",
		freq: Prand([220,220 * 1.5], 12),
		releaseTime:Pwhite(0.2,0.8),
		dur: 1/4,
		start: Pwhite(0.0,7),
		// freq: Pseq([ ref_pitch*17/15*2, ref_pitch, ref_pitch, ref_pitch*16/15],24),
		amp: Pseq(#[0.9,0.6,0.7,0.6], 16) * Pwhite(0.9, 1.1) * 2,
		rate: Pwhite(0.8,1.2)
	]
).play;
);


(
var ref_pitch = 60.midicps;

p = Pbind(
	*[
		instrument: "ss.buf.perc",
		buffer: Prand([
			// ~ss.buf['japan-cicadas']['0185-insects-water-kyoto'],
			~ss.buf['japan-cicadas']['0191-insects-temple'],
		], inf),
		releaseTime:Pwhite(0.1,0.4),
		dur: 1/8,
		start: Pwhite(0.0,7),
		// freq: Pseq([ ref_pitch*17/15*2, ref_pitch, ref_pitch, ref_pitch*16/15],24),
		amp: Pseq(#[0.9,0.6,0.7,0.6], 16) * Pwhite(0.9, 1.1) * 2,
		rate: Pwhite(0.9,1)
	]
).play;
);

p.play;
p.pause;
p.stop;




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