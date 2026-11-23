from PIL import Image, ImageDraw, ImageFont
from datetime import datetime
from pathlib import Path
import argparse, math
ROOT=Path(__file__).resolve().parent; W,H=1170,2532
def ft(n,b=False):
    paths=(["/usr/share/fonts/truetype/dejavu/DejaVuSans-Bold.ttf"] if b else ["/usr/share/fonts/truetype/dejavu/DejaVuSans.ttf"])
    for p in paths:
        try:return ImageFont.truetype(p,n)
        except:pass
    return ImageFont.load_default()
def main():
    ap=argparse.ArgumentParser();ap.add_argument("--goal",default="Job Interview");ap.add_argument("--deadline",default="2026-12-31 09:00");ap.add_argument("--progress",type=int,default=60);ap.add_argument("--output",default=str(ROOT/"assets"/"interview_zero_lock_screen_1170x2532.png"));a=ap.parse_args()
    target=datetime.strptime(a.deadline,"%Y-%m-%d %H:%M");now=datetime.now();s=max(0,int((target-now).total_seconds()));vals=[(str(s//86400),"DAYS"),(f"{s%86400//3600:02d}","HOURS"),(f"{s%3600//60:02d}","MINUTES"),(f"{s%60:02d}","SECONDS")]
    im=Image.open(ROOT/"assets"/"mountain_background.png").convert("RGB");scale=max(W/im.width,H/im.height);im=im.resize((int(im.width*scale),int(im.height*scale)));im=im.crop(((im.width-W)//2,(im.height-H)//2,(im.width+W)//2,(im.height+H)//2)).convert("RGBA")
    veil=Image.new("RGBA",(W,H),(0,0,0,0));v=ImageDraw.Draw(veil);v.rectangle((0,700,W,2350),fill=(3,8,28,140));v.rounded_rectangle((50,810,1120,2225),48,fill=(5,12,35,205),outline=(158,125,255,230),width=3);im=Image.alpha_composite(im,veil);d=ImageDraw.Draw(im)
    def c(y,t,n,col=(248,249,255,255),b=False):d.text((W//2,y),t,font=ft(n,b),fill=col,anchor="mm")
    c(160,now.strftime("%H:%M"),100);c(235,now.strftime("%a, %b %d").upper(),28,(218,226,247,255));c(885,a.goal.upper(),50,b=True);c(950,"COUNTDOWN TO YOUR NEXT OPPORTUNITY",22,(104,233,255,255),True);c(1000,target.strftime("%A, %B %d, %Y · %I:%M %p").upper(),23)
    x0=80;gap=14;bw=(W-160-42)//4
    for i,(val,lab) in enumerate(vals):
        x=x0+i*(bw+gap);d.rounded_rectangle((x,1050,x+bw,1250),25,fill=(13,25,64,240),outline=(190,132,255,255) if i%2==0 else (104,233,255,255),width=4);d.text((x+bw//2,1120),val,font=ft(58,True),fill="white",anchor="mm");d.text((x+bw//2,1195),lab,font=ft(19,True),fill=(218,226,247,255),anchor="mm")
    c(1320,f"PREPARATION PROGRESS · {max(0,min(100,a.progress))}%",28,b=True);d.rounded_rectangle((95,1350,1075,1385),18,fill=(255,255,255,55));d.rounded_rectangle((95,1350,95+980*max(0,min(100,a.progress))/100,1385),18,fill=(177,112,255,255));c(1450,"T-MINUS MILESTONES",28,(104,233,255,255),True)
    tasks=[("✓","Start preparation","NOV 30"),("✓","Build & refine resume","DEC 07"),("○","Practice common questions","DEC 14"),("○","Do mock interviews","DEC 21"),("○","Final review & mindset","DEC 30"),("★","INTERVIEW DAY","DEC 31")]
    for i,(mark,name,date) in enumerate(tasks):
        y=1535+i*100;d.text((115,y),mark,font=ft(27,True),fill=(79,224,173,255) if mark=="✓" else (190,132,255,255),anchor="mm");d.text((170,y),name,font=ft(27,True),fill="white",anchor="lm");d.text((1060,y),date,font=ft(23),fill=(218,226,247,255),anchor="rm")
    c(2190,"PREPARE · PRACTICE · PERFORM",28,b=True);Path(a.output).parent.mkdir(parents=True,exist_ok=True);im.convert("RGB").save(a.output,optimize=True);print("Saved",a.output,"1170x2532")
if __name__=="__main__":main()
