function generate {
	java -jar batik-1.8/batik-rasterizer-1.8.jar ${1} \
	-d ${3}/${4} \
	-w ${2} \
	-h ${2} \
	-bg 0.0.0.0
}

generate logo.svg 512 play-store ic_launcher.png
generate logo.svg 192 mipmap-xxxhdpi ic_launcher.png
generate logo.svg 144 mipmap-xxhdpi ic_launcher.png
generate logo.svg 96 mipmap-xhdpi ic_launcher.png
generate logo.svg 72 mipmap-hdpi ic_launcher.png
generate logo.svg 48 mipmap-mdpi ic_launcher.png