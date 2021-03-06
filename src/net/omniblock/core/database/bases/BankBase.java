package net.omniblock.core.database.bases;

import java.sql.SQLException;

import org.bukkit.entity.Player;

import net.omniblock.core.database.data.make.MakeSQLQuery;
import net.omniblock.core.database.data.make.MakeSQLUpdate;
import net.omniblock.core.database.data.make.MakeSQLUpdate.TableOperation;
import net.omniblock.core.database.data.util.Resolver;
import net.omniblock.core.database.data.util.SQLResultSet;
import net.omniblock.core.database.data.util.VariableUtils;
import net.omniblock.core.database.table.TableType;

public class BankBase {

	public static int getMoney(Player player) {

		MakeSQLQuery msq = new MakeSQLQuery(TableType.BANK_DATA).select("p_money").where("p_id",
				Resolver.getNetworkID(player));

		try {
			SQLResultSet sqr = msq.execute();
			if (sqr.next()) {
				return sqr.get("p_money");
			}
		} catch (IllegalArgumentException | SQLException e) {
			e.printStackTrace();
		}

		return VariableUtils.BANK_INITIAL_MONEY;
	}

	public static void setMoney(Player player, int quantity) {

		MakeSQLUpdate msu = new MakeSQLUpdate(TableType.BANK_DATA, TableOperation.UPDATE);

		msu.rowOperation("p_money", quantity);
		msu.whereOperation("p_id", Resolver.getNetworkID(player));

		try {

			msu.execute();
			return;

		} catch (IllegalArgumentException | SQLException e) {
			e.printStackTrace();
		}

		return;

	}

	public static void addMoney(Player player, int quantity) {

		int money = getMoney(player);
		setMoney(player, quantity + money);
		return;

	}

	public static int getExp(Player player) {

		MakeSQLQuery msq = new MakeSQLQuery(TableType.BANK_DATA).select("p_exp").where("p_id",
				Resolver.getNetworkID(player));

		try {
			SQLResultSet sqr = msq.execute();
			if (sqr.next()) {
				return sqr.get("p_exp");
			}

		} catch (IllegalArgumentException | SQLException e) {
			e.printStackTrace();
		}

		return VariableUtils.BANK_INITIAL_EXP;
	}

	public static void setExp(Player player, int quantity) {

		MakeSQLUpdate msu = new MakeSQLUpdate(TableType.BANK_DATA, TableOperation.UPDATE);

		msu.rowOperation("p_exp", quantity);
		msu.whereOperation("p_id", Resolver.getNetworkID(player));

		try {

			msu.execute();
			return;

		} catch (IllegalArgumentException | SQLException e) {
			e.printStackTrace();
		}

		return;

	}

	public static void addExp(Player player, int quantity) {

		int money = getExp(player);
		setExp(player, quantity + money);
		return;

	}

	public static int getPoints(Player player) {

		MakeSQLQuery msq = new MakeSQLQuery(TableType.BANK_DATA).select("p_vip_points").where("p_id",
				Resolver.getNetworkID(player));

		try {
			SQLResultSet sqr = msq.execute();
			if (sqr.next()) {
				return sqr.get("p_vip_points");
			}

		} catch (IllegalArgumentException | SQLException e) {
			e.printStackTrace();
		}

		return VariableUtils.BANK_INITIAL_VIP_POINTS;
	}

	public static void setPoints(Player player, int quantity) {

		MakeSQLUpdate msu = new MakeSQLUpdate(TableType.BANK_DATA, TableOperation.UPDATE);

		msu.rowOperation("p_vip_points", quantity);
		msu.whereOperation("p_id", Resolver.getNetworkID(player));

		try {

			msu.execute();
			return;

		} catch (IllegalArgumentException | SQLException e) {
			e.printStackTrace();
		}

		return;

	}

	public static void addPoints(Player player, int quantity) {

		int money = getPoints(player);
		setPoints(player, quantity + money);
		return;

	}

}
