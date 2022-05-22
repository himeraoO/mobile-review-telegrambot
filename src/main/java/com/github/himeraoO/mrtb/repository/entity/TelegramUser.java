package com.github.himeraoO.mrtb.repository.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Telegram User entity.
 */
@Setter
@Getter
@Entity
@EqualsAndHashCode
@Table(name = "tg_user")
public class TelegramUser {

    @Id
    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "active")
    private boolean active;
}

