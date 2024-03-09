#ifndef DESI_ESP_IMPORTANT_HACKS_H
#define DESI_ESP_IMPORTANT_HACKS_H

#include "socket.h"
#include "backend/Login.h"

Request request;
Response response;

float x, y;
char extra[30];
int botCount, playerCount,i;

Color clrHealth, bxclr, skeclr, headclr, clrLine, clrBox, mainskelclr,bxfillclr,bxclrfill;
Options options{};
Memory memory{false};
Color colorByDistance(int distance, float alpha){
    Color _colorByDistance;
    if (distance < 600)
        _colorByDistance = Color(0,180,0);
    if (distance < 300)
        _colorByDistance = Color(242,169,0);
    if (distance < 120)
        _colorByDistance = Color(255,0,0);
    return _colorByDistance;
}

bool colorPosCenter(float sWidth, float smMx, float sHeight, float posT, float eWidth, float emMx, float eHeight, float posB) {
    if (sWidth >= smMx && sHeight >= posT && eWidth <= emMx && eHeight <= posB) {
        return true;
    }
	    return false;
}



void DrawESP(ESP esp, int screenWidth, int screenHeight)
{

	if (isFPS)
	{       
		esp.DrawTextName(Color(255, 255, 0), "",
						 Vec2(screenWidth / 1.05, screenHeight / 13.5),
						 screenHeight / 10);
	}
	// if(isESP) {
	botCount = 0;
	playerCount = 0;
	request.ScreenHeight = screenHeight;
	request.ScreenWidth = screenWidth;
	request.Mode = InitMode;
	request.options=options;
    request.memory=memory;
	send((void *)&request, sizeof(request));
	receive((void *)&response);

	// !ResponeSuccess

	if (!response.Success)
	{

		if (isCount3)
		{
			char txtcn[200];
			sprintf(txtcn, OBFUSCATE("Enemys Nearby : 0  |  Bots Nearby : 0"));

			esp.DrawText(Color(0, 255, 0), txtcn, Vec2(screenWidth / 2, screenHeight / 11.5),
						 screenHeight / 35);
		}


		if (isCount2)
		{


			int ENEM_ICON = 2;
			int BOT_ICON = 3;

			if (playerCount == 0)
			{
				ENEM_ICON = 0;
			}
			if (botCount == 0)
			{
				BOT_ICON = 1;
			}

			char cn[10];
			sprintf(cn, "0");

			char bt[10];
			sprintf(bt, "0");



			esp.DrawOTH(Vec2(screenWidth / 2 - (80), 60), ENEM_ICON);
			esp.DrawOTH(Vec2(screenWidth / 2, 60), BOT_ICON);

			esp.DrawText(Color(255, 255, 255, 255), cn, Vec2(screenWidth / 2 - (20), 87), 23);

			esp.DrawText(Color(255, 255, 255, 255), bt, Vec2(screenWidth / 2 + (50), 87), 23);

		}

		if (isCount1)
		{
			esp.DrawEnemyCount(Color(0, 100, 255),
							   Vec2(screenWidth / 2 - screenHeight / 9, screenHeight / 16),
							   Vec2(screenWidth / 2 + screenHeight / 9, screenHeight / 10.5));
			esp.DrawText(Color().White(), OBFUSCATE("CLEAR"),
						 Vec2(screenWidth / 2, screenHeight / 11.5), screenHeight / 40);
		}

	}





	// ResponeSuccess

	if (response.Success)
	{
		float textsize = screenHeight / 50;
		for (int i = 0; i < response.PlayerCount; i++)
		{
			x = response.Players[i].HeadLocation.x;
			y = response.Players[i].HeadLocation.y;


			if (response.Players[i].isBot)
			{
				mainskelclr = Color(0, 255, 255);
				bxclrfill=Color(255, 255, 255, 50);
				botCount++;
				if (isBotWhite)
				{
					clrBox = Color(255, 255, 255);
					clrLine = Color(255, 255, 255);
				}
				else if (isBotLBlue)
				{
					clrBox = Color(0, 255, 255);
					clrLine = Color(0, 255, 255);
				}


			}
			else
			{
				playerCount++;
                bxclrfill=Color(255, 0, 0, 50);
				if (isLineRed)
				{
					clrLine = Color(255, 0, 0);
				}
				else if (isLineYellow)
				{
					clrLine = Color(255, 169, 0);
				}
				else if (isLineGreen)
				{
					clrLine = Color(0, 255, 0, 160);
				}
				else if (isLineBlue)
				{
					clrLine = Color(0, 0, 255);
				}

				if (isBoxRed)
				{
					clrBox = Color(255, 0, 0);
				}
				else if (isBoxYellow)
				{
					clrBox = Color(255, 169, 0);
				}
				else if (isBoxGreen)
				{
					clrBox = Color(0, 255, 0, 160);
				}
				else if (isBoxBlue)
				{
					clrBox = Color(0, 0, 255);
				}

				if (isSkeletonRed)
				{
					mainskelclr = Color(255, 0, 0);
				}
				else if (isSkeletonYellow)
				{
					mainskelclr = Color(255, 169, 0);
				}
				else if (isSkeletonGreen)
				{
					mainskelclr = Color(0, 255, 0, 160);
				}
				else if (isSkeletonWhite)
				{
					mainskelclr = Color(255, 255, 255);
				}


			}
			float magic_number = (response.Players[i].Distance * response.fov);
			float mx = (screenWidth / 4) / magic_number;
			float my = (screenWidth / 1.38) / magic_number;
			float top = y - my + (screenWidth / 1.7) / magic_number;
			float bottom = response.Players[i].Bone.lAn.y + my - (screenWidth / 1.7) / magic_number;
            Color _colorByDistance = colorByDistance((int)response.Players[i].Distance, 255);
            bool playerInCenter = colorPosCenter(screenWidth / 2, x - mx, screenHeight / 2, top, screenWidth / 2, x + mx, screenHeight / 2, bottom);


			if (isVisibility)
			{
				headclr = playerInCenter ? Color(255, 0, 0, 180) : Color(255, 255, 0, 180);
				skeclr = playerInCenter ? Color::Green() : mainskelclr;
				bxclr = playerInCenter ? Color::Green() : clrBox;
				bxfillclr=playerInCenter ? Color(0, 255, 0, 50) : bxclrfill;
			}
			else
			{
				headclr = Color(255, 255, 0, 180);
				skeclr = mainskelclr;
				bxclr = clrBox;
				bxfillclr=bxclrfill;
			}

			if (response.Players[i].HeadLocation.z != 1)
			{

				if (x > -50 && x < screenWidth + 50)
				{				// onScreen
                if (isSkeltonPlayer && response.Players[i].Bone.isBone && !response.Players[i].isBot)
					{			// Skelton
						esp.DrawSkelton(skeclr, 1.5, Vec2(x, y),
									 Vec2(response.Players[i].Bone.neck.x,
										  response.Players[i].Bone.neck.y));
						esp.DrawSkelton(skeclr, 1.5,
									 Vec2(response.Players[i].Bone.neck.x,
										  response.Players[i].Bone.neck.y),
									 Vec2(response.Players[i].Bone.cheast.x,
										  response.Players[i].Bone.cheast.y));
						esp.DrawSkelton(skeclr, 1.5,
									 Vec2(response.Players[i].Bone.cheast.x,
										  response.Players[i].Bone.cheast.y),
									 Vec2(response.Players[i].Bone.pelvis.x,
										  response.Players[i].Bone.pelvis.y));
						esp.DrawSkelton(skeclr, 1.5,
									 Vec2(response.Players[i].Bone.neck.x,
										  response.Players[i].Bone.neck.y),
									 Vec2(response.Players[i].Bone.lSh.x,
										  response.Players[i].Bone.lSh.y));
						esp.DrawSkelton(skeclr, 1.5,
									 Vec2(response.Players[i].Bone.neck.x,
										  response.Players[i].Bone.neck.y),
									 Vec2(response.Players[i].Bone.rSh.x,
										  response.Players[i].Bone.rSh.y));
						esp.DrawSkelton(skeclr, 1.5, Vec2(response.Players[i].Bone.lSh.x,
													   response.Players[i].Bone.lSh.y),
									 Vec2(response.Players[i].Bone.lElb.x,
										  response.Players[i].Bone.lElb.y));
						esp.DrawSkelton(skeclr, 1.5, Vec2(response.Players[i].Bone.rSh.x,
													   response.Players[i].Bone.rSh.y),
									 Vec2(response.Players[i].Bone.rElb.x,
										  response.Players[i].Bone.rElb.y));
						esp.DrawSkelton(skeclr, 1.5,
									 Vec2(response.Players[i].Bone.lElb.x,
										  response.Players[i].Bone.lElb.y),
									 Vec2(response.Players[i].Bone.lWr.x,
										  response.Players[i].Bone.lWr.y));
						esp.DrawSkelton(skeclr, 1.5,
									 Vec2(response.Players[i].Bone.rElb.x,
										  response.Players[i].Bone.rElb.y),
									 Vec2(response.Players[i].Bone.rWr.x,
										  response.Players[i].Bone.rWr.y));
						esp.DrawSkelton(skeclr, 1.5,
									 Vec2(response.Players[i].Bone.pelvis.x,
										  response.Players[i].Bone.pelvis.y),
									 Vec2(response.Players[i].Bone.lTh.x,
										  response.Players[i].Bone.lTh.y));
						esp.DrawSkelton(skeclr, 1.5,
									 Vec2(response.Players[i].Bone.pelvis.x,
										  response.Players[i].Bone.pelvis.y),
									 Vec2(response.Players[i].Bone.rTh.x,
										  response.Players[i].Bone.rTh.y));
						esp.DrawSkelton(skeclr, 1.5, Vec2(response.Players[i].Bone.lTh.x,
													   response.Players[i].Bone.lTh.y),
									 Vec2(response.Players[i].Bone.lKn.x,
										  response.Players[i].Bone.lKn.y));
						esp.DrawSkelton(skeclr, 1.5, Vec2(response.Players[i].Bone.rTh.x,
													   response.Players[i].Bone.rTh.y),
									 Vec2(response.Players[i].Bone.rKn.x,
										  response.Players[i].Bone.rKn.y));
						esp.DrawSkelton(skeclr, 1.5, Vec2(response.Players[i].Bone.lKn.x,
													   response.Players[i].Bone.lKn.y),
									 Vec2(response.Players[i].Bone.lAn.x,
										  response.Players[i].Bone.lAn.y));
						esp.DrawSkelton(skeclr, 1.5, Vec2(response.Players[i].Bone.rKn.x,
													   response.Players[i].Bone.rKn.y),
									 Vec2(response.Players[i].Bone.rAn.x,
										  response.Players[i].Bone.rAn.y));
					}
					
					if (isSkeltonPlayerBot && response.Players[i].Bone.isBone )
					{			// Skelton
						esp.DrawSkelton(skeclr, 1.5, Vec2(x, y),
									 Vec2(response.Players[i].Bone.neck.x,
										  response.Players[i].Bone.neck.y));
						esp.DrawSkelton(skeclr, 1.5,
									 Vec2(response.Players[i].Bone.neck.x,
										  response.Players[i].Bone.neck.y),
									 Vec2(response.Players[i].Bone.cheast.x,
										  response.Players[i].Bone.cheast.y));
						esp.DrawSkelton(skeclr, 1.5,
									 Vec2(response.Players[i].Bone.cheast.x,
										  response.Players[i].Bone.cheast.y),
									 Vec2(response.Players[i].Bone.pelvis.x,
										  response.Players[i].Bone.pelvis.y));
						esp.DrawSkelton(skeclr, 1.5,
									 Vec2(response.Players[i].Bone.neck.x,
										  response.Players[i].Bone.neck.y),
									 Vec2(response.Players[i].Bone.lSh.x,
										  response.Players[i].Bone.lSh.y));
						esp.DrawSkelton(skeclr, 1.5,
									 Vec2(response.Players[i].Bone.neck.x,
										  response.Players[i].Bone.neck.y),
									 Vec2(response.Players[i].Bone.rSh.x,
										  response.Players[i].Bone.rSh.y));
						esp.DrawSkelton(skeclr, 1.5, Vec2(response.Players[i].Bone.lSh.x,
													   response.Players[i].Bone.lSh.y),
									 Vec2(response.Players[i].Bone.lElb.x,
										  response.Players[i].Bone.lElb.y));
						esp.DrawSkelton(skeclr, 1.5, Vec2(response.Players[i].Bone.rSh.x,
													   response.Players[i].Bone.rSh.y),
									 Vec2(response.Players[i].Bone.rElb.x,
										  response.Players[i].Bone.rElb.y));
						esp.DrawSkelton(skeclr, 1.5,
									 Vec2(response.Players[i].Bone.lElb.x,
										  response.Players[i].Bone.lElb.y),
									 Vec2(response.Players[i].Bone.lWr.x,
										  response.Players[i].Bone.lWr.y));
						esp.DrawSkelton(skeclr, 1.5,
									 Vec2(response.Players[i].Bone.rElb.x,
										  response.Players[i].Bone.rElb.y),
									 Vec2(response.Players[i].Bone.rWr.x,
										  response.Players[i].Bone.rWr.y));
						esp.DrawSkelton(skeclr, 1.5,
									 Vec2(response.Players[i].Bone.pelvis.x,
										  response.Players[i].Bone.pelvis.y),
									 Vec2(response.Players[i].Bone.lTh.x,
										  response.Players[i].Bone.lTh.y));
						esp.DrawSkelton(skeclr, 1.5,
									 Vec2(response.Players[i].Bone.pelvis.x,
										  response.Players[i].Bone.pelvis.y),
									 Vec2(response.Players[i].Bone.rTh.x,
										  response.Players[i].Bone.rTh.y));
						esp.DrawSkelton(skeclr, 1.5, Vec2(response.Players[i].Bone.lTh.x,
													   response.Players[i].Bone.lTh.y),
									 Vec2(response.Players[i].Bone.lKn.x,
										  response.Players[i].Bone.lKn.y));
						esp.DrawSkelton(skeclr, 1.5, Vec2(response.Players[i].Bone.rTh.x,
													   response.Players[i].Bone.rTh.y),
									 Vec2(response.Players[i].Bone.rKn.x,
										  response.Players[i].Bone.rKn.y));
						esp.DrawSkelton(skeclr, 1.5, Vec2(response.Players[i].Bone.lKn.x,
													   response.Players[i].Bone.lKn.y),
									 Vec2(response.Players[i].Bone.lAn.x,
										  response.Players[i].Bone.lAn.y));
						esp.DrawSkelton(skeclr, 1.5, Vec2(response.Players[i].Bone.rKn.x,
													   response.Players[i].Bone.rKn.y),
									 Vec2(response.Players[i].Bone.rAn.x,
										  response.Players[i].Bone.rAn.y));
					}

					if (isPlayerBoxStroke) {
 	                            esp.DrawRect(bxclr,
                                        screenHeight / 500, Vec2(x - mx, top),
                                        Vec2(x + mx, bottom));
										}
										
					if (isPlayerBoxFilled) {
                                esp.DrawFilledRect(bxfillclr,
                                        Vec2(x - mx, top),
                                        Vec2(x + mx, bottom));
 	                            esp.DrawRect(bxclr,
                                        screenHeight / 500, Vec2(x - mx, top),
                                        Vec2(x + mx, bottom));
										}
					// Health
					if (isPlayerHealth)
					{

						int lhealth;

						if (isHealth1)
						{
							lhealth = 13;
						}
						else if (isHealth2)
						{
							lhealth = 30;
						}
						float healthLength = screenHeight / lhealth;

						if (healthLength < mx)
							healthLength = mx;
							
						if (response.Players[i].Health < 25)
                            if (isHealth1) {
							clrHealth = Color(255, 0, 0, 150);
                            } else {
                            clrHealth = Color(255, 0, 0);
                            }
                            
						else if (response.Players[i].Health < 50)
                            if (isHealth1) {
							clrHealth = Color(255, 165, 0, 150);
                            }else{
                            clrHealth = Color(255, 165, 0);
                            }
							
						else if (response.Players[i].Health < 75)
                            if (isHealth1) {
							clrHealth = Color(255, 255, 0, 150);
                            } else {
                            clrHealth = Color(255, 255, 0);
                            }			
						else
							if (isHealth1) {
							clrHealth = Color(0, 120, 0, 150);
                            } else {
                            clrHealth = Color(0, 255, 0);
                            }
						if (response.Players[i].Health == 0)
							esp.DrawText(Color(255, 0, 0), OBFUSCATE("Ampun OM"),
										 Vec2(x, top - screenHeight / 225), textsize);
						else
						{
							if (isHealth1)
							{
								esp.DrawFilledRect(clrHealth,
												   Vec2(x - healthLength, top - screenHeight / 30),
												   Vec2(x - healthLength +
														(2 * healthLength) *
														response.Players[i].Health / 100,
														top - screenHeight / 110));
								esp.DrawRect(Color(20,20,20), 1,
											 Vec2(x - healthLength, top - screenHeight / 30),
											 Vec2(x + healthLength, top - screenHeight / 110));
							}

							else if (isHealth2)
							{
								esp.DrawFilledRect(clrHealth,
												   Vec2(x - healthLength,
														top - screenHeight / 110),
												   Vec2(x - healthLength +
														(2 * healthLength) *
														response.Players[i].Health / 100,
														top - screenHeight / 225));

								esp.DrawRect(Color(0, 0, 0), 1.5,
											 Vec2(x - healthLength, top - screenHeight / 110),
											 Vec2(x + healthLength, top - screenHeight / 225));
							}
						}
					}
					// Head
					if (isPlayerHead)
						esp.DrawFilledCircle(headclr,
											 Vec2(response.Players[i].HeadLocation.x,
												  response.Players[i].HeadLocation.y),
											 screenHeight / 12 / magic_number);

					// Name and distance
					if (isPlayerName && response.Players[i].Health != 0) {
                        if (response.Players[i].isBot && isHealth1){
                            int AI_ICON = 5;
                            esp.DrawOTH(Vec2(x - 55, top - screenHeight / 31), AI_ICON);				
                        }
						esp.DrawName(Color().White(), response.Players[i].PlayerNameByte,response.Players[i].TeamID,
									 Vec2(x, top - screenHeight / 65), screenHeight / 60);
                    }
					
	               if (isTimID && response.Players[i].Health != 0) {
                        if (isHealth1){
                            esp.DrawTimID(Color(255, 255, 0),response.Players[i].PlayerNameByte,
									 response.Players[i].TeamID,
									 Vec2(x - screenHeight / 15.5, top - screenHeight / 65), screenHeight / 60);	
                        }else {
						    esp.DrawTimID(Color(255, 0, 0),response.Players[i].PlayerNameByte,
									 response.Players[i].TeamID,
									 Vec2(x - screenHeight / 15.5, top - screenHeight / 65), screenHeight / 60);	
                    }
					}
					
					if (isPlayerDist && response.Players[i].Health != 0)
					{
						sprintf(extra, "%0.0f m", response.Players[i].Distance);
						esp.DrawText(Color(255,180,0), extra,
									 Vec2(x, bottom + screenHeight / 60), screenHeight / 55);
					}
					// weapon
					if (isEnemyWeapon && response.Players[i].Weapon.isWeapon &&
						response.Players[i].Health != 0)
						esp.DrawWeapon(Color(247, 244, 200), response.Players[i].Weapon.id,
									   response.Players[i].Weapon.ammo,
									   Vec2(x, bottom + screenHeight / 27), screenHeight / 50);

				}


			}


			// 360 Alert

			if (response.Players[i].HeadLocation.z == 1.0f)
			{
				if (!isr360Alert)
					continue;
				if (y > screenHeight - screenHeight / 12)
					y = screenHeight - screenHeight / 12;
				else if (y < screenHeight / 12)
					y = screenHeight / 12;
				if (x < screenWidth / 2)
				{
					esp.DrawFilledCircle(_colorByDistance, Vec2(screenWidth, y),
										 screenHeight / 18);
					sprintf(extra, "%0.0f m", response.Players[i].Distance);

					esp.DrawText(Color(255, 255, 255, 255), extra,
								 Vec2(screenWidth - screenWidth / 80, y), textsize);

				}
				else
				{
					esp.DrawFilledCircle(_colorByDistance, Vec2(0, y), screenHeight / 18);
					sprintf(extra, "%0.0f m", response.Players[i].Distance);
					esp.DrawText(Color(255, 255, 255, 255), extra,
								 Vec2(screenWidth / 80, y), textsize);
				}
			}
			else if (x < -screenWidth / 10 || x > screenWidth + screenWidth / 10)
			{
				if (!isr360Alert)
					continue;
				if (y > screenHeight - screenHeight / 12)
					y = screenHeight - screenHeight / 12;
				else if (y < screenHeight / 12)
					y = screenHeight / 12;
				if (x > screenWidth / 2)
				{
					esp.DrawFilledCircle(_colorByDistance, Vec2(screenWidth, y),
										 screenHeight / 18);
					sprintf(extra, "%0.0f m", response.Players[i].Distance);

					esp.DrawText(Color(255, 255, 255, 255), extra,
								 Vec2(screenWidth - screenWidth / 80, y), textsize);

				}
				else
				{
					esp.DrawFilledCircle(_colorByDistance, Vec2(0, y), screenHeight / 18);
					sprintf(extra, "%0.0f m", response.Players[i].Distance);
					esp.DrawText(Color(255, 255, 255, 255), extra,
								 Vec2(screenWidth / 80, y), textsize);
				}
			}

			else if (isTopLine)

				if (response.Players[i].isBot)
				{
					esp.DrawLinePlayer(clrLine, 1.4,
									   Vec2(screenWidth / 2, screenHeight / 10.5), Vec2(x, top));
				}
				else
				{
					esp.DrawLinePlayer(clrLine, 1.4,
									   Vec2(screenWidth / 2, screenHeight / 10.5), Vec2(x, top));
				}


			else if (isMiddleLine)
				if (response.Players[i].isBot)
				{
					esp.DrawLinePlayer(clrLine, 1.4,
									   Vec2(screenWidth / 2, screenHeight / 2), Vec2(x, bottom));
				}
				else
				{
					esp.DrawLinePlayer(clrLine, 1.4,
									   Vec2(screenWidth / 2, screenHeight / 2), Vec2(x, bottom));
				}


			else if (isBottomLine)
				if (response.Players[i].isBot)
				{
					esp.DrawLinePlayer(clrLine, 1.4,
									   Vec2(screenWidth / 2, screenHeight / 1), Vec2(x, bottom));
				}
				else
				{
					esp.DrawLinePlayer(clrLine, 1.4,
									   Vec2(screenWidth / 2, screenHeight / 1), Vec2(x, bottom));
				}

			else if (isTopBottomLine)
				if (response.Players[i].isBot)
				{
					esp.DrawLinePlayer(clrLine, 1.4,
									   Vec2(screenWidth / 2, screenHeight / 1), Vec2(x, bottom));
					esp.DrawLinePlayer(clrLine, 1.4,
									   Vec2(screenWidth / 2, screenHeight / 10.5), Vec2(x, top));
				}
				else
				{
					esp.DrawLinePlayer(clrLine, 1.4,
									   Vec2(screenWidth / 2, screenHeight / 1), Vec2(x, bottom));
					esp.DrawLinePlayer(clrLine, 1.4,
									   Vec2(screenWidth / 2, screenHeight / 10.5), Vec2(x, top));
				}


		}










		if (isCount3)
		{
			char txtcn[200];
			sprintf(txtcn, OBFUSCATE("Enemys Nearby : %d  |  Bots Nearby : %d"), playerCount,
					botCount);

			esp.DrawText(Color(0, 255, 0), txtcn, Vec2(screenWidth / 2, screenHeight / 11.5),
						 screenHeight / 35);
		}
		if (isCount2)
		{


			int ENEM_ICON = 2;
			int BOT_ICON = 3;

			if (playerCount == 0)
			{
				ENEM_ICON = 0;
			}
			if (botCount == 0)
			{
				BOT_ICON = 1;
			}

			char cn[10];
			sprintf(cn, "%d", playerCount);

			char bt[10];
			sprintf(bt, "%d", botCount);



			esp.DrawOTH(Vec2(screenWidth / 2 - (80), 60), ENEM_ICON);
			esp.DrawOTH(Vec2(screenWidth / 2, 60), BOT_ICON);

			esp.DrawText(Color(255, 255, 255, 255), cn, Vec2(screenWidth / 2 - (20), 87), 23);

			esp.DrawText(Color(255, 255, 255, 255), bt, Vec2(screenWidth / 2 + (50), 87), 23);

		}

		if (isCount1)
		{
			if (botCount + playerCount > 0)
			{
				esp.DrawEnemyCount(Color(180, 0, 0),
								   Vec2(screenWidth / 2 - screenHeight / 9, screenHeight / 16),
								   Vec2(screenWidth / 2 + screenHeight / 9, screenHeight / 10.5));
				sprintf(extra, "%d", playerCount + botCount);
				esp.DrawText(Color().White(), extra, Vec2(screenWidth / 2, screenHeight / 11.5),
							 screenHeight / 40);
			}
			else
			{
				esp.DrawEnemyCount(Color(0, 100, 255),
								   Vec2(screenWidth / 2 - screenHeight / 9, screenHeight / 16),
								   Vec2(screenWidth / 2 + screenHeight / 9, screenHeight / 10.5));
				esp.DrawText(Color().White(), OBFUSCATE("CLEAR"),
							 Vec2(screenWidth / 2, screenHeight / 11.5), screenHeight / 40);
			}
		}


		for (int i = 0; i < response.GrenadeCount; i++)
		{
            
            if (!isGrenadeWarning)
				continue;
            if (response.Grenade[i].Location.z != 1.0f)
            {
				if (response.Grenade[i].type == 1) {
                    sprintf(extra, OBFUSCATE("Grenade (%0.0f m)"), response.Grenade[i].Distance);
			        esp.DrawText(Color(255, 0, 0, 255), extra, Vec2(response.Grenade[i].Location.x, response.Grenade[i].Location.y+20), textsize);
                    }
                else if (response.Grenade[i].type == 2) {
                    sprintf(extra, OBFUSCATE("Molotov (%0.0f m)"), response.Grenade[i].Distance);
                    esp.DrawText(Color(255, 169, 0, 255), extra, Vec2(response.Grenade[i].Location.x, response.Grenade[i].Location.y+20), textsize);
                    }
                else if (response.Grenade[i].type == 3) {
                    sprintf(extra, OBFUSCATE("Stun (%0.0f m)"), response.Grenade[i].Distance);
                    esp.DrawText(Color(255, 255, 0, 255), extra, Vec2(response.Grenade[i].Location.x, response.Grenade[i].Location.y+20), textsize);
                    }
                else {
                    sprintf(extra, OBFUSCATE("Smoke (%0.0f m)"), response.Grenade[i].Distance);
                    esp.DrawText(Color(0, 255, 0, 255), extra, Vec2(response.Grenade[i].Location.x, response.Grenade[i].Location.y+20), textsize);
                    }


            }
			
            int WARNING = 4;
            esp.DrawOTH(Vec2(screenWidth / 2 - 160, 120), WARNING);
			esp.DrawText(Color(255, 255, 255), OBFUSCATE("Warning : Throwable"),
						 Vec2(screenWidth / 2 + 20, 145), 21);
			if (response.Grenade[i].Location.z != 1.0f)
			{
				if (response.Grenade[i].type == 1)
					esp.DrawText(Color(255, 0, 0, 255), "〇",
								 Vec2(response.Grenade[i].Location.x,
									  response.Grenade[i].Location.y), textsize);
				else if (response.Grenade[i].type == 2)
					esp.DrawText(Color(255, 169, 0, 255), "〇",
								 Vec2(response.Grenade[i].Location.x,
									  response.Grenade[i].Location.y), textsize);
				else if (response.Grenade[i].type == 3)
					esp.DrawText(Color(255, 255, 0, 255), "〇",
								 Vec2(response.Grenade[i].Location.x,
									  response.Grenade[i].Location.y), textsize);
				else
					esp.DrawText(Color(0, 255, 0, 255), "〇",
								 Vec2(response.Grenade[i].Location.x,
									  response.Grenade[i].Location.y), textsize);
			}
		}


		for (int i = 0; i < response.VehicleCount; i++)
		{
			if (response.Vehicles[i].Location.z != 1.0f)
			{
				esp.DrawVehicles(response.Vehicles[i].VehicleName, response.Vehicles[i].Distance,
								 Vec2(response.Vehicles[i].Location.x,
									  response.Vehicles[i].Location.y), screenHeight / 47);

			}
		}
		for (int i = 0; i < response.ItemsCount; i++)
		{
			if (response.Items[i].Location.z != 1.0f)
			{
				esp.DrawItems(response.Items[i].ItemName, response.Items[i].Distance,
							  Vec2(response.Items[i].Location.x, response.Items[i].Location.y),
							  screenHeight / 50);

			}
		}
        
        if (options.openState == 0)//for Aimbot
                    esp.DrawCircle(Color(255,0,0), Vec2(screenWidth / 2, screenHeight / 2),
                                   options.aimingRange, 1.5);

	}
	// esp.DebugText(hello);

	// }
}


#endif // DESI_ESP_IMPORTANT_HACKS_H
