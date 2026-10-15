#!/usr/bin/env python3
"""Export the existing Android vector icon and matching store graphic using Inkscape."""
from pathlib import Path
import subprocess
import xml.etree.ElementTree as ET
root = Path(__file__).resolve().parents[1]
out = root / 'docs/store-assets'
out.mkdir(parents=True, exist_ok=True)
android = '{http://schemas.android.com/apk/res/android}'
vector = ET.parse(root / 'android-app/app/src/main/res/drawable/app_icon.xml').getroot()
paths = ''.join(f'<path fill="{p.attrib[android+"fillColor"]}" d="{p.attrib[android+"pathData"]}"/>' for p in vector)
icon = f'<svg xmlns="http://www.w3.org/2000/svg" width="512" height="512" viewBox="0 0 108 108">{paths}</svg>'
(out/'app-icon.svg').write_text(icon)
feature = f'''<svg xmlns="http://www.w3.org/2000/svg" width="1024" height="500" viewBox="0 0 1024 500">
<rect width="1024" height="500" fill="#0A0E14"/>
<path d="M645 0V500M765 0V500M885 0V500M0 125H1024M0 250H1024M0 375H1024" stroke="#162D29" stroke-width="1"/>
<circle cx="879" cy="125" r="190" fill="#10221E"/>
<circle cx="984" cy="500" r="260" fill="#0D191C"/>
<g font-family="DejaVu Sans,sans-serif">
<rect x="58" y="55" width="300" height="32" rx="16" fill="#1B382B"/>
<text x="78" y="77" fill="#64EBA1" font-size="14" font-weight="bold" letter-spacing="2">LEARN. TRY. UNDERSTAND.</text>
<text x="56" y="172" fill="#F0F8F4" font-size="57" font-weight="bold">Termux-Sathi</text>
<text x="59" y="233" fill="#B3C6BC" font-size="26">Linux learning, step by step.</text>
<text x="59" y="276" fill="#B3C6BC" font-size="22">Hindi + Hinglish. Completely offline.</text>
<rect x="59" y="329" width="178" height="48" rx="10" fill="#32D778"/>
<text x="84" y="360" fill="#08120D" font-size="19" font-weight="bold">54 lessons</text>
<text x="266" y="360" fill="#D2E8DB" font-size="19">File practice + quizzes</text>
<text x="59" y="444" fill="#8FA99B" font-size="15">Independent learning app · Network tools are simulated</text>
</g>
<g transform="translate(713 139) scale(2.2)">{paths}</g>
</svg>'''
(out/'feature-graphic.svg').write_text(feature)
for name in ('app-icon','feature-graphic'):
    subprocess.run(['inkscape',str(out/f'{name}.svg'),'--export-type=png',f'--export-filename={out/name}.png'],check=True)
print('Rendered 512×512 icon and 1024×500 feature graphic.')
