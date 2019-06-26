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
        return channelRepository.saveAndFlush(channel);
    }

}
