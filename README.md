# Features

- แอพพลิเคชั่นสามารถ Login ได้โดยใช้ Facebook

- แอพพลิเคชั่นสามารถดูเกมที่ได้รับความนิยมมากที่สุด 100 เกม ในรอบ 2 สัปดาห์

- แอพพลิเคชั่นสามารถค้นหาเกมที่ต้องการได้

- แอพพลิเคชั่นสามารถดูรายละเอียดของแต่ละเกมได้
  - ชื่อเกม
  - คำอธิบาย
  - Link ไปหน้า Steam Store
  - Link ไปที่ Website ของเกม
  - ผู้พัฒนา และ ผู้เพยแพร่
  - ราคา
  - ข่าวสารล่าสุด
  - จำนวนผู้ที่มีเกมนี้ใน Steam
  - คะแนนจาก Metacritic

- แอพพลิเคชั่นสามารถกด Like เกมได้

- แอพพลิเคชั่นสามารถกด Follow ได้

- แอพพลิเคชั่นสามารถแสดงข่าวของเกมที่ติดตามอยู่ได้

- แอพพลิเคชั่นสามารถแชร์ Link เกมไปยังแอพอื่นได้

  ​

# Interface

โดยรวมแล้วจะออกแบบตามหลัก Material Design โดยจะใช้สีหลักเป็นสีม่วงเข้ม เน้นความเรียบง่ายในการใช้งาน

### Welcome

หน้าแรกที่เข้ามา จะให้ Login ถ้ายังไม่ได้ Login ถ้า Login อยู่แล้วจะไปหน้า Home

<img src="https://github.com/patcharapon-j/final_project_android/blob/master/Screenshot/Screenshot_2017-11-30-13-52-32.jpg" width="300">



### Home

หน้าหลักของแอพ จะประกอบไปด้วย Tab ย่อย 4 อันได้แก่

#### Game

เป็นหน้าที่เอาใว้ใช้ค้นหาเกมที่ต้องการ

<img src="https://github.com/patcharapon-j/final_project_android/blob/master/Screenshot/Screenshot_2017-11-30-13-56-11.jpg" width="300">




#### Hot

เป็นห้าที่แสดงเกมที่เป็นที่นิยม 100 อันดับ ณ ตอนนี้

<img src="https://github.com/patcharapon-j/final_project_android/blob/master/Screenshot/Screenshot_2017-11-30-13-53-40.jpg" width="300">




#### News

เป็นหน้าที่แสดงข่าวของเกมที่ติดตามอยู่

<img src="https://github.com/patcharapon-j/final_project_android/blob/master/Screenshot/Screenshot_2017-11-30-13-55-23.jpg" width="300">



#### Profile

หน้าแสดงข้อมูลส่วนตัว เกมที่ติดตามอยู่ และใช้ Logout

<img src="https://github.com/patcharapon-j/final_project_android/blob/master/Screenshot/Screenshot_2017-11-30-13-55-08.jpg" width="300">



### Game Detail

ถ้าคลิกที่ Card ของเกมในหน้าใดก็ตาม จะเข้าไปยังหน้ารายละอียดของเกม จะมีปุ่มเอาไว้ Share, Like, Follow  ซึ่งภายในมี 3 tab ได้แก่

#### Info

แสดงข้อมูลของเกมจาก Steam

<img src="https://github.com/patcharapon-j/final_project_android/blob/master/Screenshot/Screenshot_2017-11-30-13-53-59.jpg" width="300">
<img src="https://github.com/patcharapon-j/final_project_android/blob/master/Screenshot/Screenshot_2017-11-30-13-54-04.jpg" width="300">



#### Stats

แสดงของมูลทางสถิติจาก Steamspy

<img src="https://github.com/patcharapon-j/final_project_android/blob/master/Screenshot/Screenshot_2017-11-30-13-54-14.jpg" width="300">




#### News

แสดงข่าวสารของเกมนั้น ๆ

<img src="https://github.com/patcharapon-j/final_project_android/blob/master/Screenshot/Screenshot_2017-11-30-13-54-21.jpg" width="300">



# APIs

รายละเอียดของ API ที่ใช้

## Facebook Login API

ใช้ในการ Login ด้วย Facebook และดึงข้อมูลผู้ใช้

**!!!ตอนนี้การ Login ด้วย Facebook มีปัญหา (การการค้นหาทราบว่า เป็นกันหลายคน น่าเป็นเป็นที่ Facebook) ทำให้ Login ด้วย Web ไม่ได้ แต่การ Login ผ่าน Facebook App ทำได้ตามปกติ**

```java
this.mCallbackManager = CallbackManager.Factory.create();
loginButton = findViewById(R.id.login_button);
loginButton.setReadPermissions("email", "public_profile");
loginButton.setLoginBehavior(LoginBehavior.NATIVE_ONLY);
loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
    @Override
    public void onSuccess(LoginResult loginResult) {
        Log.i("Debug", loginResult.toString());
        authenticationViewModel.authenticate(loginResult.getAccessToken());
        showActivityIndicator();
    }

    @Override
    public void onCancel() {
        Log.i("Debug", "Cancel");
    }

    @Override
    public void onError(FacebookException error) {
        Log.d("Debug", error.toString());
        showLoginFailToast();

    }
});
```



## Steam Web API

เป็น API ของทาง Steam ใช้ในการดึงข้อมูลรายละเอียดของแต่ละเกม รวมไปถึงข่าวนสารต่าง ๆ

### ดึงข้อมูลแต่ละเกม

```html
http://store.steampowered.com/api/appdetails?appids={appid}
```

#### Parameter

- appid : ไอดีของเกม

#### Response

```json
{
    "440": {
        "success": true,
        "data": {
            "type": "game",
            "name": "Team Fortress 2",
            "steam_appid": 440,
            "required_age": 0,
            "is_free": true,
            "dlc": [
                629330
            ],
            "detailed_description": "<h1>The Jungle Inferno Update</h1><p><a href=\"https://steamcommunity.com/linkfilter/?url=http://www.teamfortress.com/jungleinferno/\" target=\"_blank\" rel=\"noopener\"  >Play the all-new Jungle Inferno Campaign! </a><br><ul class=\"bb_ul\"><li> 1 new official map: Mercenary Park<br></li><li> 5 featured community maps: Mossrock, Lazarus, Brazil, Enclosure, and Banana Bay<br></li><li> 2 new official taunts: Yeti Punch and Yeti Smash<br></li><li> 5 new community taunts: The Dueling Banjo, The Jumping Jack, The Soviet Strongarm, The Russian Arms Race, and The Headcase<br></li><li> 2 community cosmetic cases with 20 items each<br></li><li> 2 new War Paint collections made up of community-made War Paints and official War Paints<br></li><li> 2 new War Paint collections filled with classic weapon skins being brought back for another tour<br></li><li> 4 new Pyro items: The Dragon's Fury, The Thermal Thruster (with a new kill taunt: The Gas Blast), The Gas Passer, and the Hot Hand<br></li><li> 1 new Heavy item: The Second Banana<br></li><li> Free contracts to earn the new Pyro and Heavy items<br></li><li> 36 campaign contracts</li></ul><a href=\"https://steamcommunity.com/linkfilter/?url=http://www.teamfortress.com/jungleinferno/\" target=\"_blank\" rel=\"noopener\"  ><img src=\"http://cdn.edgecast.steamstatic.com/steam/apps/440/extras/jungle_inferno_blog.jpg?t=1508547684\" ></a></p><br><h1>About the Game</h1><p><strong>\"The most fun you can have online\"</strong> - PC Gamer<br><br>\t\t\t\t\t\t\t\t\t <strong>Is now FREE!</strong><br><br>\t\t\t\t\t\t\t\t\t There’s no catch! Play as much as you want, as long as you like!</p><br>\t\t\t\t\t\t\t\t\t <p><strong>The most highly-rated free game of all time!</strong><br><br>\t\t\t\t\t\t\t\t\t One of the most popular online action games of all time, Team Fortress 2 delivers constant free updates—new game modes, maps, equipment and, most importantly, hats. Nine distinct classes provide a broad range of tactical abilities and personalities, and lend themselves to a variety of player skills.</p><br>\t\t\t\t\t\t\t\t\t <p><strong>New to TF? Don’t sweat it!</strong><br><br>\t\t\t\t\t\t\t\t\t No matter what your style and experience, we’ve got a character for you. Detailed training and offline practice modes will help you hone your skills before jumping into one of TF2’s many game modes, including Capture the Flag, Control Point, Payload, Arena, King of the Hill and more.</p><br>\t\t\t\t\t\t\t\t\t <p><strong>Make a character your own!</strong><br><br>\t\t\t\t\t\t\t\t\t There are hundreds of weapons, hats and more to collect, craft, buy and trade. Tweak your favorite class to suit your gameplay style and personal taste. You don’t need to pay to win—virtually all of the items in the Mann Co. Store can also be found in-game.</p><br>\t\t\t\t\t\t\t\t\t<a href=\"<a href=\"https://steamcommunity.com/linkfilter/?url=http://www.teamfortress.com/freetoplay\" target=\"_blank\" rel=\"noopener\"  >http://www.teamfortress.com/freetoplay</a>\"><img src=\"<a href=\"http://storefront.steampowered.com/v/gfx/apps/440/extras/page_banner_english1.jpg\" target=\"_blank\" rel=\"noreferrer\"  >http://storefront.steampowered.com/v/gfx/apps/440/extras/page_banner_english1.jpg</a>\"></a><br>\t\t\t\t\t\t\t\t\t",
            "about_the_game": "<p><strong>\"The most fun you can have online\"</strong> - PC Gamer<br>\r\n\t\t\t\t\t\t\t\t\t <strong>Is now FREE!</strong><br>\r\n\t\t\t\t\t\t\t\t\t There’s no catch! Play as much as you want, as long as you like!</p>\r\n\t\t\t\t\t\t\t\t\t <p><strong>The most highly-rated free game of all time!</strong><br>\r\n\t\t\t\t\t\t\t\t\t One of the most popular online action games of all time, Team Fortress 2 delivers constant free updates—new game modes, maps, equipment and, most importantly, hats. Nine distinct classes provide a broad range of tactical abilities and personalities, and lend themselves to a variety of player skills.</p>\r\n\t\t\t\t\t\t\t\t\t <p><strong>New to TF? Don’t sweat it!</strong><br>\r\n\t\t\t\t\t\t\t\t\t No matter what your style and experience, we’ve got a character for you. Detailed training and offline practice modes will help you hone your skills before jumping into one of TF2’s many game modes, including Capture the Flag, Control Point, Payload, Arena, King of the Hill and more.</p>\r\n\t\t\t\t\t\t\t\t\t <p><strong>Make a character your own!</strong><br>\r\n\t\t\t\t\t\t\t\t\t There are hundreds of weapons, hats and more to collect, craft, buy and trade. Tweak your favorite class to suit your gameplay style and personal taste. You don’t need to pay to win—virtually all of the items in the Mann Co. Store can also be found in-game.</p>\r\n\t\t\t\t\t\t\t\t\t<a href=\"http://www.teamfortress.com/freetoplay\"><img src=\"http://storefront.steampowered.com/v/gfx/apps/440/extras/page_banner_english1.jpg\"></a>\r\n\t\t\t\t\t\t\t\t\t",
            "short_description": "Nine distinct classes provide a broad range of tactical abilities and personalities.  Constantly updated with new game modes, maps, equipment and, most importantly, hats!",
            "supported_languages": "English<strong>*</strong>, Danish, Dutch, Finnish, French, German, Italian, Japanese, Norwegian, Polish, Portuguese, Russian, Simplified Chinese, Spanish, Swedish, Traditional Chinese, Korean, Czech, Hungarian, Portuguese-Brazil, Turkish, Greek, Bulgarian, Romanian, Thai, Ukrainian<br><strong>*</strong>languages with full audio support",
            "header_image": "http://cdn.akamai.steamstatic.com/steam/apps/440/header.jpg?t=1508547684",
            "website": "http://www.teamfortress.com/",
            "pc_requirements": {
                "minimum": "<strong>Minimum:</strong><br><ul class=\"bb_ul\"><li><strong>OS:</strong> Windows® 7 (32/64-bit)/Vista/XP<br></li><li><strong>Processor:</strong> 1.7 GHz Processor or better<br></li><li><strong>Memory:</strong> 512 MB RAM<br></li><li><strong>DirectX:</strong> Version 8.1<br></li><li><strong>Network:</strong> Broadband Internet connection<br></li><li><strong>Storage:</strong> 15 GB available space</li></ul>",
                "recommended": "<strong>Recommended:</strong><br><ul class=\"bb_ul\"><li><strong>OS:</strong> Windows® 7 (32/64-bit)<br></li><li><strong>Processor:</strong> Pentium 4 processor (3.0GHz, or better)<br></li><li><strong>Memory:</strong> 1 GB RAM<br></li><li><strong>DirectX:</strong> Version 9.0c<br></li><li><strong>Network:</strong> Broadband Internet connection<br></li><li><strong>Storage:</strong> 15 GB available space</li></ul>"
            },
            "mac_requirements": {
                "minimum": "<strong>Minimum:</strong><br><ul class=\"bb_ul\"><li><strong>OS:</strong> OS X version Leopard 10.5.8 and above<br></li><li><strong>Processor:</strong> 1.7 GHz Processor or better<br></li><li><strong>Memory:</strong> 1 GB RAM<br></li><li><strong>Graphics:</strong> NVIDIA GeForce 8 or higher, ATI X1600 or higher, Intel HD 3000 or higher<br></li><li><strong>Network:</strong> Broadband Internet connection<br></li><li><strong>Storage:</strong> 15 GB available space</li></ul>"
            },
            "linux_requirements": {
                "minimum": "<strong>Minimum:</strong><br><ul class=\"bb_ul\"><li><strong>OS:</strong> Ubuntu 12.04<br></li><li><strong>Processor:</strong> Dual core from Intel or AMD at 2.8 GHz<br></li><li><strong>Memory:</strong> 1 GB RAM<br></li><li><strong>Graphics:</strong> nVidia GeForce 8600/9600GT, ATI/AMD Radeon HD2600/3600 (Graphic Drivers: nVidia 310, AMD 12.11), OpenGL 2.1<br></li><li><strong>Network:</strong> Broadband Internet connection<br></li><li><strong>Storage:</strong> 15 GB available space<br></li><li><strong>Sound Card:</strong> OpenAL Compatible Sound Card</li></ul>"
            },
            "developers": [
                "Valve"
            ],
            "publishers": [
                "Valve"
            ],
            "packages": [
                469
            ],
            "package_groups": [
                {
                    "name": "default",
                    "title": "Buy Team Fortress 2",
                    "description": "",
                    "selection_text": "Select a purchase option",
                    "save_text": "",
                    "display_type": 0,
                    "is_recurring_subscription": "false",
                    "subs": [
                        {
                            "packageid": 469,
                            "percent_savings_text": "",
                            "percent_savings": 0,
                            "option_text": "The Orange Box - $19.99 USD",
                            "option_description": "",
                            "can_get_free_license": "0",
                            "is_free_license": false,
                            "price_in_cents_with_discount": 1999
                        }
                    ]
                }
            ],
            "platforms": {
                "windows": true,
                "mac": true,
                "linux": true
            },
            "metacritic": {
                "score": 92,
                "url": "http://www.metacritic.com/game/pc/team-fortress-2?ftag=MCD-06-10aaa1f"
            },
            "categories": [
                {
                    "id": 1,
                    "description": "Multi-player"
                },
                {
                    "id": 27,
                    "description": "Cross-Platform Multiplayer"
                },
                {
                    "id": 22,
                    "description": "Steam Achievements"
                },
                {
                    "id": 29,
                    "description": "Steam Trading Cards"
                },
                {
                    "id": 13,
                    "description": "Captions available"
                },
                {
                    "id": 30,
                    "description": "Steam Workshop"
                },
                {
                    "id": 35,
                    "description": "In-App Purchases"
                },
                {
                    "id": 18,
                    "description": "Partial Controller Support"
                },
                {
                    "id": 8,
                    "description": "Valve Anti-Cheat enabled"
                },
                {
                    "id": 15,
                    "description": "Stats"
                },
                {
                    "id": 17,
                    "description": "Includes level editor"
                },
                {
                    "id": 14,
                    "description": "Commentary available"
                }
            ],
            "genres": [
                {
                    "id": "1",
                    "description": "Action"
                },
                {
                    "id": "37",
                    "description": "Free to Play"
                }
            ],
            "screenshots": [
                {
                    "id": 0,
                    "path_thumbnail": "http://cdn.akamai.steamstatic.com/steam/apps/440/ss_ea21f7bbf4f79bada4554df5108d04b6889d3453.600x338.jpg?t=1508547684",
                    "path_full": "http://cdn.akamai.steamstatic.com/steam/apps/440/ss_ea21f7bbf4f79bada4554df5108d04b6889d3453.1920x1080.jpg?t=1508547684"
                },
                {
                    "id": 1,
                    "path_thumbnail": "http://cdn.akamai.steamstatic.com/steam/apps/440/ss_e3aedb2ab36bba8cfe611b1e0eaa807e4bb2d742.600x338.jpg?t=1508547684",
                    "path_full": "http://cdn.akamai.steamstatic.com/steam/apps/440/ss_e3aedb2ab36bba8cfe611b1e0eaa807e4bb2d742.1920x1080.jpg?t=1508547684"
                },
                {
                    "id": 2,
                    "path_thumbnail": "http://cdn.akamai.steamstatic.com/steam/apps/440/ss_ee24a769dc1d81dcbd7b250d16530394adee4264.600x338.jpg?t=1508547684",
                    "path_full": "http://cdn.akamai.steamstatic.com/steam/apps/440/ss_ee24a769dc1d81dcbd7b250d16530394adee4264.1920x1080.jpg?t=1508547684"
                },
                {
                    "id": 3,
                    "path_thumbnail": "http://cdn.akamai.steamstatic.com/steam/apps/440/ss_9faaa506d91bf19dbb398e0c06a684b337f85f91.600x338.jpg?t=1508547684",
                    "path_full": "http://cdn.akamai.steamstatic.com/steam/apps/440/ss_9faaa506d91bf19dbb398e0c06a684b337f85f91.1920x1080.jpg?t=1508547684"
                },
                {
                    "id": 4,
                    "path_thumbnail": "http://cdn.akamai.steamstatic.com/steam/apps/440/ss_7de978f22a7059151c31d0488dc57c5c7703c48b.600x338.jpg?t=1508547684",
                    "path_full": "http://cdn.akamai.steamstatic.com/steam/apps/440/ss_7de978f22a7059151c31d0488dc57c5c7703c48b.1920x1080.jpg?t=1508547684"
                },
                {
                    "id": 5,
                    "path_thumbnail": "http://cdn.akamai.steamstatic.com/steam/apps/440/ss_e79f2490af3247b4b0f8d412d437b72c321cfe3b.600x338.jpg?t=1508547684",
                    "path_full": "http://cdn.akamai.steamstatic.com/steam/apps/440/ss_e79f2490af3247b4b0f8d412d437b72c321cfe3b.1920x1080.jpg?t=1508547684"
                },
                {
                    "id": 6,
                    "path_thumbnail": "http://cdn.akamai.steamstatic.com/steam/apps/440/ss_8f28c75172e3d08e65222725202b82f58bfe7df7.600x338.jpg?t=1508547684",
                    "path_full": "http://cdn.akamai.steamstatic.com/steam/apps/440/ss_8f28c75172e3d08e65222725202b82f58bfe7df7.1920x1080.jpg?t=1508547684"
                },
                {
                    "id": 7,
                    "path_thumbnail": "http://cdn.akamai.steamstatic.com/steam/apps/440/0000002574.600x338.jpg?t=1508547684",
                    "path_full": "http://cdn.akamai.steamstatic.com/steam/apps/440/0000002574.1920x1080.jpg?t=1508547684"
                },
                {
                    "id": 8,
                    "path_thumbnail": "http://cdn.akamai.steamstatic.com/steam/apps/440/0000002575.600x338.jpg?t=1508547684",
                    "path_full": "http://cdn.akamai.steamstatic.com/steam/apps/440/0000002575.1920x1080.jpg?t=1508547684"
                },
                {
                    "id": 9,
                    "path_thumbnail": "http://cdn.akamai.steamstatic.com/steam/apps/440/0000002576.600x338.jpg?t=1508547684",
                    "path_full": "http://cdn.akamai.steamstatic.com/steam/apps/440/0000002576.1920x1080.jpg?t=1508547684"
                },
                {
                    "id": 10,
                    "path_thumbnail": "http://cdn.akamai.steamstatic.com/steam/apps/440/0000002577.600x338.jpg?t=1508547684",
                    "path_full": "http://cdn.akamai.steamstatic.com/steam/apps/440/0000002577.1920x1080.jpg?t=1508547684"
                },
                {
                    "id": 11,
                    "path_thumbnail": "http://cdn.akamai.steamstatic.com/steam/apps/440/0000002578.600x338.jpg?t=1508547684",
                    "path_full": "http://cdn.akamai.steamstatic.com/steam/apps/440/0000002578.1920x1080.jpg?t=1508547684"
                },
                {
                    "id": 12,
                    "path_thumbnail": "http://cdn.akamai.steamstatic.com/steam/apps/440/0000002579.600x338.jpg?t=1508547684",
                    "path_full": "http://cdn.akamai.steamstatic.com/steam/apps/440/0000002579.1920x1080.jpg?t=1508547684"
                },
                {
                    "id": 13,
                    "path_thumbnail": "http://cdn.akamai.steamstatic.com/steam/apps/440/0000002580.600x338.jpg?t=1508547684",
                    "path_full": "http://cdn.akamai.steamstatic.com/steam/apps/440/0000002580.1920x1080.jpg?t=1508547684"
                },
                {
                    "id": 14,
                    "path_thumbnail": "http://cdn.akamai.steamstatic.com/steam/apps/440/0000002581.600x338.jpg?t=1508547684",
                    "path_full": "http://cdn.akamai.steamstatic.com/steam/apps/440/0000002581.1920x1080.jpg?t=1508547684"
                },
                {
                    "id": 15,
                    "path_thumbnail": "http://cdn.akamai.steamstatic.com/steam/apps/440/?t=1508547684",
                    "path_full": "http://cdn.akamai.steamstatic.com/steam/apps/440/?t=1508547684"
                }
            ],
            "movies": [
                {
                    "id": 256698790,
                    "name": "Jungle Inferno",
                    "thumbnail": "http://cdn.akamai.steamstatic.com/steam/apps/256698790/movie.293x165.jpg?t=1508541859",
                    "webm": {
                        "480": "http://cdn.akamai.steamstatic.com/steam/apps/256698790/movie480.webm?t=1508541859",
                        "max": "http://cdn.akamai.steamstatic.com/steam/apps/256698790/movie_max.webm?t=1508541859"
                    },
                    "highlight": true
                },
                {
                    "id": 81943,
                    "name": "Mann vs Machine_Trailer",
                    "thumbnail": "http://cdn.akamai.steamstatic.com/steam/apps/81943/movie.293x165.jpg?t=1447796690",
                    "webm": {
                        "480": "http://cdn.akamai.steamstatic.com/steam/apps/81943/movie480.webm?t=1447796690",
                        "max": "http://cdn.akamai.steamstatic.com/steam/apps/81943/movie_max.webm?t=1447796690"
                    },
                    "highlight": false
                },
                {
                    "id": 81800,
                    "name": "Meet the Pyro - TF2 ",
                    "thumbnail": "http://cdn.akamai.steamstatic.com/steam/apps/81800/movie.293x165.jpg?t=1447796700",
                    "webm": {
                        "480": "http://cdn.akamai.steamstatic.com/steam/apps/81800/movie480.webm?t=1447796700",
                        "max": "http://cdn.akamai.steamstatic.com/steam/apps/81800/movie_max.webm?t=1447796700"
                    },
                    "highlight": false
                },
                {
                    "id": 80924,
                    "name": "Team Fortress 2 Free to Play",
                    "thumbnail": "http://cdn.akamai.steamstatic.com/steam/apps/80924/movie.293x165.jpg?t=1447796720",
                    "webm": {
                        "480": "http://cdn.akamai.steamstatic.com/steam/apps/80924/movie480.webm?t=1447796720",
                        "max": "http://cdn.akamai.steamstatic.com/steam/apps/80924/movie_max.webm?t=1447796720"
                    },
                    "highlight": false
                },
                {
                    "id": 80923,
                    "name": "Team Fortress 2: Meet The Medic",
                    "thumbnail": "http://cdn.akamai.steamstatic.com/steam/apps/80923/movie.293x165.jpg?t=1447796710",
                    "webm": {
                        "480": "http://cdn.akamai.steamstatic.com/steam/apps/80923/movie480.webm?t=1447796710",
                        "max": "http://cdn.akamai.steamstatic.com/steam/apps/80923/movie_max.webm?t=1447796710"
                    },
                    "highlight": false
                },
                {
                    "id": 5734,
                    "name": "TF2 Mac Trailer",
                    "thumbnail": "http://cdn.akamai.steamstatic.com/steam/apps/5734/movie.293x165.jpg?t=1447796732",
                    "webm": {
                        "480": "http://cdn.akamai.steamstatic.com/steam/apps/5734/movie480.webm?t=1447796732",
                        "max": "http://cdn.akamai.steamstatic.com/steam/apps/5734/movie_max.webm?t=1447796732"
                    },
                    "highlight": false
                },
                {
                    "id": 5260,
                    "name": "Team Fortress 2: Meet the Spy",
                    "thumbnail": "http://cdn.akamai.steamstatic.com/steam/apps/5260/movie.293x165.jpg?t=1447797085",
                    "webm": {
                        "480": "http://cdn.akamai.steamstatic.com/steam/apps/5260/movie480.webm?t=1447797085",
                        "max": "http://cdn.akamai.steamstatic.com/steam/apps/5260/movie_max.webm?t=1447797085"
                    },
                    "highlight": false
                },
                {
                    "id": 5073,
                    "name": "Team Fortress 2: Meet the Sandvich",
                    "thumbnail": "http://cdn.akamai.steamstatic.com/steam/apps/5073/movie.jpg?t=1447797037",
                    "webm": {
                        "480": "http://cdn.akamai.steamstatic.com/steam/apps/5073/movie480.webm?t=1447797037",
                        "max": "http://cdn.akamai.steamstatic.com/steam/apps/5073/movie_max.webm?t=1447797037"
                    },
                    "highlight": false
                },
                {
                    "id": 5051,
                    "name": "Team Fortress 2: Meet the Sniper",
                    "thumbnail": "http://cdn.akamai.steamstatic.com/steam/apps/5051/movie.293x165.jpg?t=1447796944",
                    "webm": {
                        "480": "http://cdn.akamai.steamstatic.com/steam/apps/5051/movie480.webm?t=1447796944",
                        "max": "http://cdn.akamai.steamstatic.com/steam/apps/5051/movie_max.webm?t=1447796944"
                    },
                    "highlight": false
                },
                {
                    "id": 5032,
                    "name": "Team Fortress 2: Meet the Scout (English)",
                    "thumbnail": "http://cdn.akamai.steamstatic.com/steam/apps/5032/movie.jpg?t=1447796873",
                    "webm": {
                        "480": "http://cdn.akamai.steamstatic.com/steam/apps/5032/movie480.webm?t=1447796873",
                        "max": "http://cdn.akamai.steamstatic.com/steam/apps/5032/movie_max.webm?t=1447796873"
                    },
                    "highlight": false
                },
                {
                    "id": 997,
                    "name": "Team Fortress 2: Meet the Demoman",
                    "thumbnail": "http://cdn.akamai.steamstatic.com/steam/apps/997/movie.jpg?t=1447796837",
                    "webm": {
                        "480": "http://cdn.akamai.steamstatic.com/steam/apps/997/movie480.webm?t=1447796837",
                        "max": "http://cdn.akamai.steamstatic.com/steam/apps/997/movie_max.webm?t=1447796837"
                    },
                    "highlight": false
                },
                {
                    "id": 987,
                    "name": "Team Fortress 2: Meet the Engineer",
                    "thumbnail": "http://cdn.akamai.steamstatic.com/steam/apps/987/movie.jpg?t=1447796800",
                    "webm": {
                        "480": "http://cdn.akamai.steamstatic.com/steam/apps/987/movie480.webm?t=1447796800",
                        "max": "http://cdn.akamai.steamstatic.com/steam/apps/987/movie_max.webm?t=1447796800"
                    },
                    "highlight": false
                },
                {
                    "id": 985,
                    "name": "Team Fortress 2: Meet the Soldier (English)",
                    "thumbnail": "http://cdn.akamai.steamstatic.com/steam/apps/985/movie.jpg?t=1447796787",
                    "webm": {
                        "480": "http://cdn.akamai.steamstatic.com/steam/apps/985/movie480.webm?t=1447796787",
                        "max": "http://cdn.akamai.steamstatic.com/steam/apps/985/movie_max.webm?t=1447796787"
                    },
                    "highlight": false
                },
                {
                    "id": 960,
                    "name": "Team Fortress 2: Meet the Heavy",
                    "thumbnail": "http://cdn.akamai.steamstatic.com/steam/apps/960/movie.jpg?t=1447796774",
                    "webm": {
                        "480": "http://cdn.akamai.steamstatic.com/steam/apps/960/movie480.webm?t=1447796774",
                        "max": "http://cdn.akamai.steamstatic.com/steam/apps/960/movie_max.webm?t=1447796774"
                    },
                    "highlight": false
                },
                {
                    "id": 931,
                    "name": "Team Fortress 2 Trailer 2",
                    "thumbnail": "http://cdn.akamai.steamstatic.com/steam/apps/931/movie.jpg?t=1447796762",
                    "webm": {
                        "480": "http://cdn.akamai.steamstatic.com/steam/apps/931/movie480.webm?t=1447796762",
                        "max": "http://cdn.akamai.steamstatic.com/steam/apps/931/movie_max.webm?t=1447796762"
                    },
                    "highlight": false
                },
                {
                    "id": 2030252,
                    "name": "Mann vs. Machine - The Sound of Medicine",
                    "thumbnail": "http://cdn.akamai.steamstatic.com/steam/apps/2030252/movie.293x165.jpg?t=1447796679",
                    "webm": {
                        "480": "http://cdn.akamai.steamstatic.com/steam/apps/2030252/movie480.webm?t=1447796679",
                        "max": "http://cdn.akamai.steamstatic.com/steam/apps/2030252/movie_max.webm?t=1447796679"
                    },
                    "highlight": false
                }
            ],
            "recommendations": {
                "total": 486082
            },
            "achievements": {
                "total": 520,
                "highlighted": [
                    {
                        "name": "Head of the Class",
                        "path": "http://cdn.akamai.steamstatic.com/steamcommunity/public/images/apps/440/tf_play_game_everyclass.jpg"
                    },
                    {
                        "name": "World Traveler",
                        "path": "http://cdn.akamai.steamstatic.com/steamcommunity/public/images/apps/440/tf_play_game_everymap.jpg"
                    },
                    {
                        "name": "Team Doctor",
                        "path": "http://cdn.akamai.steamstatic.com/steamcommunity/public/images/apps/440/tf_get_healpoints.jpg"
                    },
                    {
                        "name": "Flamethrower",
                        "path": "http://cdn.akamai.steamstatic.com/steamcommunity/public/images/apps/440/tf_burn_playersinminimumtime.jpg"
                    },
                    {
                        "name": "Sentry Gunner",
                        "path": "http://cdn.akamai.steamstatic.com/steamcommunity/public/images/apps/440/tf_get_turretkills.jpg"
                    },
                    {
                        "name": "Nemesis",
                        "path": "http://cdn.akamai.steamstatic.com/steamcommunity/public/images/apps/440/tf_kill_nemesis.jpg"
                    },
                    {
                        "name": "Hard to Kill",
                        "path": "http://cdn.akamai.steamstatic.com/steamcommunity/public/images/apps/440/tf_get_consecutivekills_nodeaths.jpg"
                    },
                    {
                        "name": "Master of Disguise",
                        "path": "http://cdn.akamai.steamstatic.com/steamcommunity/public/images/apps/440/tf_get_healed_byenemy.jpg"
                    },
                    {
                        "name": "With Friends Like these...",
                        "path": "http://cdn.akamai.steamstatic.com/steamcommunity/public/images/apps/440/tf_play_game_friendsonly.jpg"
                    },
                    {
                        "name": "Dynasty",
                        "path": "http://cdn.akamai.steamstatic.com/steamcommunity/public/images/apps/440/tf_win_multiplegames.jpg"
                    }
                ]
            },
            "release_date": {
                "coming_soon": false,
                "date": "10 Oct, 2007"
            },
            "support_info": {
                "url": "http://steamcommunity.com/app/440",
                "email": ""
            },
            "background": "http://cdn.akamai.steamstatic.com/steam/apps/440/page_bg_generated_v6b.jpg?t=1508547684"
        }
    }
}
```



### ดึงข่าวสารของแต่ละเกม

```
http://api.steampowered.com/ISteamNews/GetNewsForApp/v2/?appid=420&count=20&maxlength=200
```

#### Parameter

- appid : ไอดีของเกม
- count : จำนวนข่าวที่ต้องการ
- maxlenght :  ความยาวสูงสุดของเนื้อหาข่าว

#### Response

```json
{
    "appnews": {
        "appid": 420,
        "newsitems": [
            {
                "gid": "84840457396423719",
                "title": "You know why there s no Half-Life 3, but here it is again",
                "url": "http://store.steampowered.com/news/externalpost/rps/84840457396423719",
                "is_external_url": true,
                "author": "contact@rockpapershotgun.com (Alice O'Connor)",
                "contents": "",
                "feedlabel": "Rock, Paper, Shotgun",
                "date": 1484248393,
                "feedname": "rps",
                "feed_type": 0,
                "appid": 0
            },
            {
                "gid": "371904442450658915",
                "title": "The Last Eight Years Of Gordon Freeman s Unpublished Diaries RPS Exclusive",
                "url": "http://store.steampowered.com/news/externalpost/rps/371904442450658915",
                "is_external_url": true,
                "author": "contact@rockpapershotgun.com (John Walker)",
                "contents": "<em>An envelope arrived in the post this morning. Thick, stuffed with books. Diaries, in fact. Someone has sent me Gordon Freeman&#8217;s diaries from the last eight years. I don&#8217;t really know what ...</em>",
                "feedlabel": "Rock, Paper, Shotgun",
                "date": 1445515201,
                "feedname": "rps",
                "feed_type": 0,
                "appid": 0
            },
            {
                "gid": "517122602977159788",
                "title": "The Long-Lost Characters Of Half-Life 2",
                "url": "http://store.steampowered.com/news/externalpost/rps/517122602977159788",
                "is_external_url": true,
                "author": "contact@rockpapershotgun.com (Alec Meer)",
                "contents": "The leaked Half-Life 2 beta is an old, old story &#8211; and how it happened, and what happened next was <a href=\"http://www.eurogamer.net/articles/2011-02-21-the-boy-who-stole-half-life-2-article\">documented masterfully</a> by RPS chum Simon Parking a few years back &#8211; but a recent fan comp...",
                "feedlabel": "Rock, Paper, Shotgun",
                "date": 1423771231,
                "feedname": "rps",
                "feed_type": 0,
                "appid": 0
            },
            {
                "gid": "521613514460643429",
                "title": "Mod of the Week: Steam, Tracks, Trouble and Riddles for Half-Life 2: Episode 2",
                "url": "http://store.steampowered.com/news/externalpost/pcgamer/521613514460643429",
                "is_external_url": true,
                "author": "",
                "contents": "<a href=\"http://www.pcgamer.com/2014/09/28/mod-of-the-week-steam-tracks-trouble-riddles-for-half-life-2-episode-2/\" rel=\"bookmark\" title=\"Permanent Link to Mod of the Week: Steam, Tracks, Trouble and Riddles for Half-Life 2: Episode 2\"> </a> There have been a lot of visions of the post-apocalypse, but if you've been waiting for one in which a sleepy-voiced robot guides you through a series train-based environmental puzzles in the year 2...",
                "feedlabel": "PC Gamer",
                "date": 1411894800,
                "feedname": "pcgamer",
                "feed_type": 0,
                "appid": 0
            },
            {
                "gid": "567767801883369366",
                "title": "Half-Life 2 review November 2004, UK edition",
                "url": "http://store.steampowered.com/news/externalpost/pcgamer/567767801883369366",
                "is_external_url": true,
                "author": "",
                "contents": "<a href=\"http://www.pcgamer.com/review/half-life-2-review-june-2005-uk-edition/\" rel=\"bookmark\" title=\"Permanent Link to Half-Life 2 review November 2004, UK edition\"> </a> Every week, we publish a classic PC Gamer review from the '90s or early 2000s. This week, Ben Griffin provides context and commentary followed by the full, original text of our Half-Life 2 review, p...",
                "feedlabel": "PC Gamer",
                "date": 1404724599,
                "feedname": "pcgamer",
                "feed_type": 0,
                "appid": 0
            },
            {
                "gid": "3171973017167458522",
                "title": "Mod of the Week: The Citizen Returns, for Half-Life 2, Episode 2",
                "url": "http://store.steampowered.com/news/externalpost/pcgamer/3171973017167458522",
                "is_external_url": true,
                "author": "",
                "contents": "<a href=\"http://www.pcgamer.com/2014/06/29/mod-of-the-week-the-citizen-returns-for-half-life-2-episode-2/\" rel=\"bookmark\" title=\"Permanent Link to Mod of the Week: The Citizen Returns, for Half-Life 2, Episode 2\"> </a> The Citizen Returns, a mod for Half-Life 2: Episode 2, is a sequel to a 2008 mod called The Citizen. They're both about, as you may have guessed, a citizen. He's trying to escape City 17 at the same...",
                "feedlabel": "PC Gamer",
                "date": 1404032442,
                "feedname": "pcgamer",
                "feed_type": 0,
                "appid": 0
            },
            {
                "gid": "3188858977897174962",
                "title": "“I did see some concept art for Half-Life 3″ says Counter-Strike co-creator. Left 4 Dead 3 “looks great”",
                "url": "http://store.steampowered.com/news/externalpost/pcgamer/3188858977897174962",
                "is_external_url": true,
                "author": "",
                "contents": "<a href=\"http://www.pcgamer.com/2014/05/23/i-did-see-some-concept-art-for-half-life-3-says-counter-strike-creator-left-4-dead-3-looks-great/\" rel=\"bookmark\" title=\"Permanent Link to &#8220;I did see some concept art for Half-Life 3&#8243; says Counter-Strike co-creator. Left 4 Dead 3 &#8220;looks great&#8221;\"> </a> Counter-Strike creator Minh Le has been talking to goRGNtv about Valve's most anticipated projects. He's seen artwork of Valve's next Half-Life game, and more of Left 4 Dead 3, which has been rumour...",
                "feedlabel": "PC Gamer",
                "date": 1400843008,
                "feedname": "pcgamer",
                "feed_type": 0,
                "appid": 0
            },
            {
                "gid": "920167588138345101",
                "title": "Report lists Steam’s most popular (and most untouched) games",
                "url": "http://store.steampowered.com/news/externalpost/pcgamer/920167588138345101",
                "is_external_url": true,
                "author": "",
                "contents": "<a href=\"http://www.pcgamer.com/2014/04/17/steam-popular-unplayed/\" rel=\"bookmark\" title=\"Permanent Link to Report lists Steam&#8217;s most popular (and most untouched) games\"> </a> Have you played every single game in your Steam library? No? Neither have I and that accomplishment is apparently just a small sand grain in the over 288 million games in Steam collections that have...",
                "feedlabel": "PC Gamer",
                "date": 1397759714,
                "feedname": "pcgamer",
                "feed_type": 0,
                "appid": 0
            },
            {
                "gid": "1462832954619975315",
                "title": "Half-Life 3 trademark filed by Valve",
                "url": "http://store.steampowered.com/news/externalpost/pcgamer/1462832954619975315",
                "is_external_url": true,
                "author": "",
                "contents": "<a href=\"http://www.pcgamer.com/2013/10/02/half-life-3-trademark-filed-by-valve/\" rel=\"bookmark\" title=\"Permanent Link to Half-Life 3 trademark filed by Valve\"> </a> Concept art source: ValveTime.net Update: It seems as if we were duped. The trademark filing has since been removed, suggesting its initial listing was a hoax. Original story below. Here's some smal...",
                "feedlabel": "PC Gamer",
                "date": 1380706341,
                "feedname": "pcgamer",
                "feed_type": 0,
                "appid": 0
            },
            {
                "gid": "1478588482003999418",
                "title": "Half-Life 2 mod Radiator 1-1: Polaris being remade as standalone game",
                "url": "http://store.steampowered.com/news/externalpost/pcgamer/1478588482003999418",
                "is_external_url": true,
                "author": "",
                "contents": "<a href=\"http://www.pcgamer.com/2013/07/10/half-life-2-mod-radiator-1-1-polaris-being-remade-as-standalone-game-2/\" rel=\"bookmark\" title=\"Permanent Link to Half-Life 2 mod Radiator 1-1: Polaris being remade as standalone game\"> </a> Robert Yang's experimental Half-Life 2 mod series Radiator is due to be repurposed as a pack of short-form single-player games, starting with a \"slightly longer\" standalone remake of the original ep...",
                "feedlabel": "PC Gamer",
                "date": 1373456529,
                "feedname": "pcgamer",
                "feed_type": 0,
                "appid": 0
            },
            {
                "gid": "1478591738299474740",
                "title": "Half-Life 2: Episode Two updated",
                "url": "http://store.steampowered.com/news/externalpost/steam_community_announcements/1478591738299474740",
                "is_external_url": true,
                "author": "alfred",
                "contents": "We have updated the public release of Half-Life 2: Episode Two. This update contains all the changes from the recent beta, thanks to the whole community for their help with testing and suggesting new ...",
                "feedlabel": "Community Announcements",
                "date": 1372278401,
                "feedname": "steam_community_announcements",
                "feed_type": 1,
                "appid": 0
            },
            {
                "gid": "1497726886956546063",
                "title": "Double Action mod alpha side-dives out",
                "url": "http://store.steampowered.com/news/externalpost/shacknews/1497726886956546063",
                "is_external_url": true,
                "author": "Alice O'Connor",
                "contents": "Double Action: Boogaloo may have missed its <a href=\"http://www.shacknews.com/article/78174/the-specialists-mod-team-kickstarting-free-spiritual-successor\">crowdfunding</a> goal to fund full-time development, but it continues in slow-motion. The John Woo-y HL2 mod, whose makers include folks behind Half-Life mod Th...",
                "feedlabel": "Shacknews",
                "date": 1372053600,
                "feedname": "shacknews",
                "feed_type": 0,
                "appid": 0
            },
            {
                "gid": "1459441364949562731",
                "title": "Half Life 2 Magnifici-Mod MINERVA, The Director’s Cut",
                "url": "http://store.steampowered.com/news/externalpost/rps/1459441364949562731",
                "is_external_url": true,
                "author": "contact@rockpapershotgun.com (Alec Meer)",
                "contents": "I hate Adam Foster, creator of last decade&#8217;s rapturously-received Half-Life 2 mod series <a href=\"https://hylobatidae.org/minerva/\">MINERVA</a> (not to be confused with BioShock 2: Minerva&#8217;s Den) and more recently a Valve employee. I h...",
                "feedlabel": "Rock, Paper, Shotgun",
                "date": 1367316053,
                "feedname": "rps",
                "feed_type": 0,
                "appid": 0
            },
            {
                "gid": "1459437403490742994",
                "title": "Zombie Master 2 mod shambles out",
                "url": "http://store.steampowered.com/news/externalpost/shacknews/1459437403490742994",
                "is_external_url": true,
                "author": "Alice O'Connor",
                "contents": "You try hurrying when your knees are splintered and tear rotten flesh with every step. After almost three years of development, Source mod Zombie Master 2 launched over the weekend. The follow-up drag...",
                "feedlabel": "Shacknews",
                "date": 1363633200,
                "feedname": "shacknews",
                "feed_type": 0,
                "appid": 0
            },
            {
                "gid": "1459437403491789184",
                "title": "Zombie Master 2 mod shambles out",
                "url": "http://store.steampowered.com/news/externalpost/shacknews/1459437403491789184",
                "is_external_url": true,
                "author": "Alice O'Connor",
                "contents": "You try hurrying when your knees are splintered and tear rotten flesh with every step. After almost three years of development, Source mod Zombie Master 2 launched over the weekend. The follow-up drag...",
                "feedlabel": "Shacknews",
                "date": 1363633200,
                "feedname": "shacknews",
                "feed_type": 0,
                "appid": 0
            },
            {
                "gid": "1459437403333847522",
                "title": "Gabe Newell: Valve’s business increased by 50 percent last year",
                "url": "http://store.steampowered.com/news/externalpost/pcgamer/1459437403333847522",
                "is_external_url": true,
                "author": "",
                "contents": "<a href=\"http://www.pcgamer.com/2013/03/14/gabe-newell-valve-steam-bafta/\" rel=\"bookmark\" title=\"Permanent Link to Gabe Newell: Valve&#8217;s business increased by 50 percent last year\"> </a> Valve boss Gabe Newell stepped up to the stage during last week's BAFTA awards to receive the prestigious Academy Fellowship for his contributions to gaming. Presumably momentarily distracted by acc...",
                "feedlabel": "PC Gamer",
                "date": 1363284913,
                "feedname": "pcgamer",
                "feed_type": 0,
                "appid": 0
            },
            {
                "gid": "1495465667608565717",
                "title": "The Half-Life 2 Spherical Nightmares mod is good and you should play it",
                "url": "http://store.steampowered.com/news/externalpost/pcgamer/1495465667608565717",
                "is_external_url": true,
                "author": "",
                "contents": "<a href=\"http://www.pcgamer.com/2013/03/08/the-half-life-2-spherical-nightmares-mod-is-good-and-you-should-play-it/\" rel=\"bookmark\" title=\"Permanent Link to The Half-Life 2 Spherical Nightmares mod is good and you should play it\"> </a> Some mods want to re-invent the game they're based on, but when that game is Half-Life 2, there's no need. In mods like Minerva (highly recommended, if you haven't played it), the creators treat Hal...",
                "feedlabel": "PC Gamer",
                "date": 1362763847,
                "feedname": "pcgamer",
                "feed_type": 0,
                "appid": 0
            },
            {
                "gid": "1496591477368701890",
                "title": "Garry’s Mod has earned nearly $22 million over seven years",
                "url": "http://store.steampowered.com/news/externalpost/pcgamer/1496591477368701890",
                "is_external_url": true,
                "author": "",
                "contents": "<a href=\"http://www.pcgamer.com/2013/03/06/garrys-mod-sales/\" rel=\"bookmark\" title=\"Permanent Link to Garry&#8217;s Mod has earned nearly $22 million over seven years\"> </a> Source: http://bit.ly/XQp76W Garry's Mod, that wonderful physics sandbox of posable characters doing very silly things, has done rather well since attaching a $10 price for its tomfoolery back in 20...",
                "feedlabel": "PC Gamer",
                "date": 1362597599,
                "feedname": "pcgamer",
                "feed_type": 0,
                "appid": 0
            },
            {
                "gid": "1497714835828796891",
                "title": "J.J. Abrams says Half-Life and Portal movie idea is 'real,' promises collaboration",
                "url": "http://store.steampowered.com/news/externalpost/shacknews/1497714835828796891",
                "is_external_url": true,
                "author": "Andrew Yoon",
                "contents": "Kicking off the DICE summit in Las Vegas today was a keynote presentation by Valve head Gabe Newell and <i>Star Trek</i> director J.J. Abrams. At the talk, the two teased that they were interested in working...",
                "feedlabel": "Shacknews",
                "date": 1360155600,
                "feedname": "shacknews",
                "feed_type": 0,
                "appid": 0
            },
            {
                "gid": "1497714199074729863",
                "title": "Half-Life 2 modded for Oculus Rift support",
                "url": "http://store.steampowered.com/news/externalpost/pcgamer/1497714199074729863",
                "is_external_url": true,
                "author": "",
                "contents": "<a href=\"http://www.pcgamer.com/2013/02/04/half-life-2-modded-for-oculus-rift-support/\" rel=\"bookmark\" title=\"Permanent Link to Half-Life 2 modded for Oculus Rift support\"> </a> What happens when you combine (no pun intended) the easily modified Source engine with a penchant for homebrew tech? This VR tracking mod for Half-Life 2, designed to let players step into the orang...",
                "feedlabel": "PC Gamer",
                "date": 1359982098,
                "feedname": "pcgamer",
                "feed_type": 0,
                "appid": 0
            }
        ],
        "count": 58
    }
}
```



## Steam Spy API

ใช้ในการดึงข้อมูลเชิงสถิติจาก SteamSpy

```
steamspy.com/api.php?request=appdetails&appid=440
```

#### Parameter

- appid : ไอดีของเกม

#### Response

```json
{
    "appid": 440,
    "name": "Team Fortress 2",
    "developer": "Valve",
    "publisher": "Valve",
    "score_rank": 85,
    "positive": 456688,
    "negative": 29364,
    "userscore": 93,
    "owners": 43816273,
    "owners_variance": 194219,
    "players_forever": 43816273,
    "players_forever_variance": 194219,
    "players_2weeks": 1724316,
    "players_2weeks_variance": 40330,
    "average_forever": 4505,
    "average_2weeks": 658,
    "median_forever": 226,
    "median_2weeks": 377,
    "price": "0",
    "ccu": 50259,
    "tags": {
        "Free to Play": 16579,
        "Multiplayer": 9849,
        "FPS": 8617,
        "Action": 7701,
        "Shooter": 7262,
        "Class-Based": 5706,
        "Team-Based": 4902,
        "Funny": 4741,
        "First-Person": 4438,
        "Trading": 4268,
        "Cartoony": 3946,
        "Competitive": 3823,
        "Co-op": 3652,
        "Online Co-Op": 3649,
        "Robots": 2873,
        "Comedy": 2777,
        "Tactical": 2487,
        "Crafting": 2273,
        "Cartoon": 2223,
        "Moddable": 2068
    }
}
```



## Firebase

ใช้ Firebase มนการเป็น Backend ของแอพพลิเคชั่น ซึ่งคอยจัดการเกี่ยวกับการ Authentication และ บันทึกข้อมูล

### Authentication

ใช้ Firebase ในการ สร้างและจัดการข้อมูลของผู้ใช้

```java
AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("Debug", "signInWithCredential:success");
                            listener.onAuthenticationSuccessful();
                        } else {
                            Log.w("Debug", "signInWithCredential:failure", task.getException());
                            listener.onAuthenticationFailure();
                        }
                    }
                });
```



### Realtime Database

ใช้เก็บข้อมูลการกด Like และ การ Follow ของแต่ละ User



# Video Presentation

*ส่งถายในวันที่ 4*



# APK File

https://drive.google.com/open?id=1xYLud2Pyp-KdopaYTQ2A0ezQi2FOdnuq



# Test Summary

## Unit Test

### gameDetailWebsiteNullTest

#### เงื่อนไข

ข้อมูล Website ของ GameModel เป็น null

#### ผลลัพท์ที่ต้องการ

"N/A"



### gameDetailWebsiteNormalTest

#### เงื่อนไข

ข้อมูล Website ของ GameModel เป็น "www.google.com"

#### ผลลัพท์ที่ต้องการ

"www.google.com"



### gameDetailDeveloperNullTest

#### เงื่อนไข

ข้อมูล Developer ของ GameModel เป็น null

#### ผลลัพท์ที่ต้องการ

"N/A"



### gameDetailDeveloperNormalTest

#### เงื่อนไข

ข้อมูล Developer ของ GameModel เป็น "Me"

#### ผลลัพท์ที่ต้องการ

"Me"



### gameDetailPublisherNullTest

#### เงื่อนไข

ข้อมูล Publisher ของ GameModel เป็น "null"

#### ผลลัพท์ที่ต้องการ

"N/A"



### gameDetailPublisherNormalTest

#### เงื่อนไข

ข้อมูล Publisher ของ GameModel เป็น "Valve"

#### ผลลัพท์ที่ต้องการ

"Valve"



### gameDetailDescriptionNullTest

#### เงื่อนไข

ข้อมูล Description ของ GameModel เป็น null

#### ผลลัพท์ที่ต้องการ

"N/A"



### gameDetailDescriptionNormalTest

#### เงื่อนไข

ข้อมูล Description ของ GameModel เป็น "Description"

#### ผลลัพท์ที่ต้องการ

"Description"



### gameDetailPriceCoversionNormalTest

#### เงื่อนไข

ข้อมูล Price ของ GameModel เป็น 1299

#### ผลลัพท์ที่ต้องการ

"12.99$"



### gameDetailPriceCoversionCentTest

#### เงื่อนไข

ข้อมูล Price ของ GameModel เป็น 99

#### ผลลัพท์ที่ต้องการ

"0.99$"



### gameDetailPriceCoversionFreeTest

#### เงื่อนไข

ข้อมูล Price ของ GameModel เป็น 0

#### ผลลัพท์ที่ต้องการ

"Free"



### gameDetailPriceCoversionLeadingZeroTest

#### เงื่อนไข

ข้อมูล Price ของ GameModel เป็น 1009

#### ผลลัพท์ที่ต้องการ

"10.09$"



### gameDetailSupportedPlatformOneTest

#### เงื่อนไข

ข้อมูล GameModel รองรับแค่ Windows

#### ผลลัพท์ที่ต้องการ

"Windows"



### gameDetailSupportedPlatformTwoTest

#### เงื่อนไข

ข้อมูล GameModel รองรับแค่ Windows และ Mac

#### ผลลัพท์ที่ต้องการ

"Windows Mac"



### gameDetailSupportedPlatformAllTest

#### เงื่อนไข

ข้อมูล GameModel รองรับทั้ง 3 Platform

#### ผลลัพท์ที่ต้องการ

"Windows Linux Mac"



### gameDetailDateComingSoon

#### เงื่อนไข

ข้อมูล Date ของ GameModel เป็น Coming Soon

#### ผลลัพท์ที่ต้องการ

"Coming Soon"



# Review

https://www.youtube.com/watch?v=wyjUyavf1sE
