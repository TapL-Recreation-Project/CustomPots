package me.swipez.custompots.utils;

import com.google.common.base.Preconditions;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * A utility class to assist in the creation of ItemStacks in the confines of a single line.
 *
 * @author Parker Hawke - Choco
 */
public final class PotionItemBuilder {

    private final ItemStack item;
    private final PotionMeta meta;

    private PotionItemBuilder(Material type, int amount) {
        Preconditions.checkArgument(type != null, "Cannot create ItemBuilder for null Material");
        Preconditions.checkArgument(type.isItem(), "Illegal material!");

        this.item = new ItemStack(type);
        this.meta = (PotionMeta) item.getItemMeta();
        this.amount(amount);
    }

    private PotionItemBuilder(ItemStack item) {
        Preconditions.checkArgument(item != null, "Cannot modify a null item");
        Preconditions.checkArgument(item.getType().isItem(), "Illegal material!");

        this.item = item.clone();
        this.meta = (PotionMeta) item.getItemMeta();
    }

    /**
     * Get a new instance of an ItemBuilder given a (non-null and non-air)
     * {@link Material} and a quantity greater than 0 and less than or equal to
     * {@link Material#getMaxStackSize()}.
     *
     * @param type the type of item to build
     * @param amount the item amount
     *
     * @return the ItemBuilder instance for the provided values
     */
    public static PotionItemBuilder of(Material type, int amount) {
        return new PotionItemBuilder(type, amount);
    }

    /**
     * Get a new instance of an ItemBuilder given a (non-null and non-air)
     * {@link Material}.
     *
     * @param type the type of item to build
     *
     * @return the ItemBuilder instance for the provided material
     */
    public static PotionItemBuilder of(Material type) {
        return new PotionItemBuilder(type, 1);
    }

    /**
     * Get a new instance of ItemBuilder to modify an existing {@link ItemStack}. The
     * ItemStack passed will be cloned, therefore the passed reference will not be
     * modified, but rather a copy of it. The result of {@link #build()} will be a
     * separate item with the changes applied from this builder instance. The provided
     * item acts as a base for the values in this builder.
     *
     * @param item the item to build
     *
     * @return the ItemBuilder instance for the provided item
     */
    public static PotionItemBuilder modify(ItemStack item) {
        return new PotionItemBuilder(item);
    }

    /**
     * Check whether the specified type of ItemMeta is supported by this ItemBuilder.
     *
     * @param type the type of meta to check
     *
     * @return true if supported, false otherwise or if null
     */
    public boolean isSupportedMeta(Class<? extends ItemMeta> type) {
        return type != null && type.isInstance(meta);
    }

    /**
     * Apply a method from a more specific type of ItemMeta to this ItemBuilder instance.
     * If the type provided is unsupported by this ItemBuilder (according to
     * {@link #isSupportedMeta(Class)}), this method will throw an exception, therefore it
     * is recommended that it be checked before invoking this method if you are unsure as
     * to what is and is not supported.
     *
     * @param type the type of ItemMeta to apply
     * @param applier the function to apply to the ItemMeta instance
     * @param <T> The ItemMeta type to be applied in the consumer function
     *
     * @return this instance. Allows for chained method calls
     */
    public <T extends ItemMeta> PotionItemBuilder specific(Class<T> type, Consumer<T> applier) {
        Preconditions.checkArgument(type != null, "Cannot apply meta for type null");
        Preconditions.checkArgument(isSupportedMeta(type), "The specified ItemMeta type is not supported by this ItemBuilder instance");
        Preconditions.checkArgument(applier != null, "Application function must not be null");

        applier.accept(type.cast(meta));
        return this;
    }

    /**
     * Apply data to the item's {@link PersistentDataContainer}.
     *
     * @param applier the function to apply to the persistent data container
     *
     * @return this instance. Allows for chained method calls
     */
    public PotionItemBuilder applyPersistentData(Consumer<PersistentDataContainer> applier) {
        Preconditions.checkArgument(applier != null, "Application function must not be null");

        applier.accept(meta.getPersistentDataContainer());
        return this;
    }

    /**
     * Set the item name.
     *
     * @param name the name to set
     *
     * @return this instance. Allows for chained method calls
     *
     * @see ItemMeta#setDisplayName(String)
     */
    public PotionItemBuilder name(String name) {
        this.meta.setDisplayName(name);
        return this;
    }

    /**
     * Set the item lore in the form of varargs.
     *
     * @param lore the lore to set
     *
     * @return this instance. Allows for chained method calls
     *
     * @see PotionItemBuilder#lore(List)
     * @see ItemMeta#setLore(List)
     */
    public PotionItemBuilder lore(String... lore) {
        if (lore == null || lore.length > 0) {
            this.meta.setLore(Arrays.asList(lore));
        }

        return this;
    }

    /**
     * Set the item lore in the form of a {@literal List<String>}.
     *
     * @param lore the lore to set
     *
     * @return this instance. Allows for chained method calls
     *
     * @see PotionItemBuilder#lore(String...)
     * @see ItemMeta#setLore(List)
     */
    public PotionItemBuilder lore(List<String> lore) {
        this.meta.setLore(lore);
        return this;
    }

    /**
     * Set the item damage. Some items may not display damage or accept the damage
     * attribute at all, in which case this method will simply fail silently.
     *
     * @param damage the damage to set
     *
     * @return this instance. Allows for chained method calls
     *
     * @see Damageable#setDamage(int)
     */
    public PotionItemBuilder damage(int damage) {
        ((Damageable) meta).setDamage(damage);
        return this;
    }

    /**
     * Set the item amount. This damage must range between 1 and
     * {@link Material#getMaxStackSize()} according to the type being built in this
     * ItemBuilder instance.
     *
     * @param amount the amount to set
     *
     * @return this instance. Allows for chained method calls
     *
     * @see ItemStack#setAmount(int)
     */
    public PotionItemBuilder amount(int amount) {
        this.item.setAmount(amount);
        return this;
    }

    /**
     * Apply an enchantment with the specified level to the item. This method does not
     * respect the level limitations of an enchantment (i.e. Sharpness VI may be applied
     * if desired).
     *
     * @param enchantment the enchantment to add
     * @param level the enchantment level to set
     *
     * @return this instance. Allows for chained method calls
     *
     * @see ItemMeta#addEnchant(Enchantment, int, boolean)
     */
    public PotionItemBuilder enchantment(Enchantment enchantment, int level) {
        this.meta.addEnchant(enchantment, level, true);
        return this;
    }

    /**
     * Add an attribute modifier to this item.
     *
     * @param attribute the attribute for which to add a modifier
     * @param modifier the modifier to apply
     *
     * @return this instance. Allows for chained method calls
     */
    public PotionItemBuilder attribute(Attribute attribute, AttributeModifier modifier) {
        this.meta.addAttributeModifier(attribute, modifier);
        return this;
    }

    /**
     * Apply flags to the item.
     *
     * @param flags the flags to set
     *
     * @return this instance. Allows for chained method calls
     *
     * @see ItemMeta#addItemFlags(ItemFlag...)
     */
    public PotionItemBuilder flags(ItemFlag... flags) {
        if (flags.length > 0) {
            this.meta.addItemFlags(flags);
        }

        return this;
    }

    /**
     * Set the unbreakable state of this item to true.
     *
     * @return this instance. Allows for chained method calls
     *
     * @see ItemMeta#setUnbreakable(boolean)
     */
    public PotionItemBuilder unbreakable() {
        this.meta.setUnbreakable(true);
        return this;
    }

    /**
     * Set the item's localized name.
     *
     * @param name the localized name to set
     *
     * @return this instance. Allows for chained method calls
     *
     * @see ItemMeta#setLocalizedName(String)
     */
    public PotionItemBuilder localizedName(String name) {
        this.meta.setLocalizedName(name);
        return this;
    }

    /**
     * Set the item's custom model data flag.
     *
     * @param data the data to set
     *
     * @return this instance. Allows for chained method calls
     *
     * @see ItemMeta#setCustomModelData(Integer)
     */
    public PotionItemBuilder modelData(int data) {
        this.meta.setCustomModelData(data);
        return this;
    }

    public PotionItemBuilder color(Color color) {
        this.meta.setColor(color);
        return this;
    }

    /**
     * Complete the building of this ItemBuilder and return the resulting ItemStack.
     *
     * @return the completed {@link ItemStack} instance built by this builder
     */
    public ItemStack build() {
        this.item.setItemMeta(meta);
        return item;
    }

}
