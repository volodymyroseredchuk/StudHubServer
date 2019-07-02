package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.entity.Channel;
import com.softserve.academy.studhub.repository.ChannelRepository;
import com.softserve.academy.studhub.service.ChannelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    private ChannelRepository channelRepository;

    @Override
    public Channel save(Channel channel) {
        if (channel != null) {
            if (channel.getQuestion() != null) {
                return channelRepository.saveAndFlush(channel);
            } else {
                throw new IllegalArgumentException("Question in channel cannot be empty.");
            }
        } else {
            throw new IllegalArgumentException("Channel cannot be empty.");
        }
    }

}
