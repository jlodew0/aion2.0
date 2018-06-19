/*
 * This file is part of aion-unique <aion-unique.org>.
 *
 * aion-unique is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * aion-unique is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with aion-unique.  If not, see <http://www.gnu.org/licenses/>.
 */
package quest.eltnen;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

/**
 * @author Atomics
 * 
 */
public class _1376AMountaineOfTrouble extends QuestHandler
{

	private final static int	questId	= 1376;

	public _1376AMountaineOfTrouble()
	{
		super(questId);
	}

	@Override
	public void register()
	{
		qe.setNpcQuestData(203947).addOnQuestStart(questId); //Beramones
		qe.setNpcQuestData(203947).addOnTalkEvent(questId); //Beramones
		qe.setNpcQuestData(203964).addOnTalkEvent(questId); //Agrips
		qe.setNpcQuestData(210976).addOnKillEvent(questId); // Kerubien Hunter
		qe.setNpcQuestData(210986).addOnKillEvent(questId); // Kerubien Hunter
	}

	@Override
	public boolean onDialogEvent(QuestEnv env)
	{
		final Player player = env.getPlayer();
		int targetId = 0;
		
		if(env.getVisibleObject() instanceof Npc)
			targetId = ((Npc) env.getVisibleObject()).getNpcId();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if(qs == null || qs.getStatus() == QuestStatus.NONE)
		{
			if(targetId == 203947) //Beramones
			{
				if(env.getDialogId() == 25)
					return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
				else
					return defaultQuestStartDialog(env);
			}
		}
		else if(qs.getStatus() == QuestStatus.REWARD)
		{
			if(targetId == 203964) //Agrips
			{
			    if (env.getDialogId() == -1)
			    	return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
			    return defaultQuestEndDialog(env);
			}
		}
		return false;
	}
	@Override
	public boolean onKillEvent(QuestEnv env)
	{
		Player player = env.getPlayer();
		QuestState qs = player.getQuestStateList().getQuestState(questId);
		if(qs == null || qs.getStatus() != QuestStatus.START)
			return false;

		int var = qs.getQuestVarById(0);
		int targetId = 0;
		if(env.getVisibleObject() instanceof Npc)
			targetId = ((Npc) env.getVisibleObject()).getNpcId();
		switch(targetId)
		{
			case 210976:
				if(var >= 0 && var < 6)
				{
					qs.setQuestVarById(0, var + 1);
					updateQuestStatus(player, qs);
					return true;
				}
				else if(var == 6)
				{
					qs.setStatus(QuestStatus.REWARD);
					updateQuestStatus(player, qs);
					return true;
				}
			case 210986:
				if(var >= 0 && var < 6)
				{
					qs.setQuestVarById(0, var + 1);
					updateQuestStatus(player, qs);
					return true;
				}
				else if(var == 6)
				{
					qs.setStatus(QuestStatus.REWARD);
					updateQuestStatus(player, qs);
					return true;
				}
		}
		return false;
	}
}