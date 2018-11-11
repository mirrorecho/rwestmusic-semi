
(

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



SynthDef(\bloo, {
	arg freq = 440;
	var temp, sum=0, numBloo=16;
	numBloo.do{
		arg count;
		temp = SinOsc.ar(
			freq
			* LFNoise2.kr({Rand(0.2, 4)}!2).exprange(0.99, 1.01)
			* (count + 1),
			mul: 1 / ((count+1)/2)
		);
		temp = temp * LFNoise2.kr({Rand(0.5, 4)}!2).exprange(0.01, 1);
		sum = sum + temp;
	};
	sum = (sum / numBloo) * 0.8;
	sum = FreeVerb2.ar(sum[0], sum[1]);
	Out.ar(0, sum);
}).add;


)


	